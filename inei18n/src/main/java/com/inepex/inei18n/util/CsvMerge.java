package com.inepex.inei18n.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class CsvMerge {

    /**
     * @param csvFileTarget
     * @param targetColumn
     *            - 0 based
     * @param targetSep
     *            - separator like: ;
     * @param csvFilePatch
     * @param patchColumn
     *            - 0 based
     * @param patchSep
     *            - separator like: ;
     * @param resultFile
     *            - can be equal to csvFilePatch or csvFileTarget
     * @param resultSep
     *            - separator like: ;
     * @param dummyCellValue
     *            - like "" (two ") or space
     */
    public static void merge(
        String csvFileTarget,
        int targetColumn,
        String targetSep,
        String csvFilePatch,
        int patchColumn,
        String patchSep,
        String resultFile,
        String resultSep,
        String dummyCellValue) {

        System.out.println("INFO: Parsing target!");
        ParsedCsv target = parseCsv(getLines(csvFileTarget), targetSep);
        System.out.println("INFO: Parsing patch!");
        ParsedCsv patch = parseCsv(getLines(csvFilePatch), patchSep);

        if (targetColumn >= target.columnCount)
            System.out.println("WARNING: the target has only "
                + target.columnCount
                + " columns, it's going to completed to "
                + targetColumn
                + " wide");

        System.out.println("INFO: merging");
        MergeResult res = merge(target, targetColumn, patch, patchColumn, dummyCellValue);

        System.out.println("INFO: storing");
        storeMergedCsv(resultFile, resultSep, res.resultLines);
        printInfo(res, target, patch);
    }

    // ********************** LOGIC
    public static ParsedCsv parseCsv(List<String> lines, String sep) {
        if (lines.size() < 1)
            throw new IllegalArgumentException("The list of lines can not be empty!");

        if (sep == null || sep.length() < 1)
            throw new IllegalArgumentException(
                "Separator must be one or more than one character long!");

        TreeMap<String, String[]> resMap = new TreeMap<String, String[]>();
        int colCount = lines.get(0).split(sep).length;

        int i = 0;
        for (String str : lines) {
            i++;
            String[] splitted = csvSeparatorSplitLogic(str, sep);
            if (splitted.length != colCount)
                throw new RuntimeException("There are "
                    + splitted.length
                    + " colums in the line "
                    + i
                    + " (expected: "
                    + colCount
                    + ")");

            String key = splitted[0];
            if (resMap.keySet().contains(key))
                throw new RuntimeException("Duplicated key :" + key + " in line " + i);

            resMap.put(key, splitted);
        }

        return new ParsedCsv(colCount, resMap);
    }

    public static MergeResult merge(
        ParsedCsv target,
        int targetColumn,
        ParsedCsv patch,
        int patchColumn,
        String dummyCellValue) {
        if (patchColumn >= patch.columnCount)
            throw new IllegalArgumentException("The patch has only "
                + patch.columnCount
                + " columns, and the selected is the "
                + patchColumn);

        MergeResult res = new MergeResult(new LinkedList<String>(), new LinkedList<String[]>());

        for (String targetKey : target.parsedLines.keySet()) {
            String[] targetLine = target.parsedLines.get(targetKey);
            String[] patchLine = patch.parsedLines.get(targetKey);

            String[] resultLine;
            if (targetColumn > target.columnCount) {
                resultLine = new String[targetColumn + 1];
                for (int i = target.columnCount; i < targetColumn + 1; i++) {
                    resultLine[i] = dummyCellValue;
                }
            } else {
                resultLine = new String[target.columnCount];
            }

            for (int i = 0; i < target.columnCount; i++) {
                resultLine[i] = targetLine[i];
            }

            if (patchLine != null) {
                res.keyMatchLines.add(targetKey);
                if (!dummyCellValue.equals(patchLine[patchColumn])) {
                    resultLine[targetColumn] = patchLine[patchColumn];
                } else {
                    res.dummy++;
                }
            }
            res.resultLines.add(resultLine);
        }
        return res;
    }

    // ************* file IO and printInfo
    private static void printInfo(MergeResult res, ParsedCsv target, ParsedCsv patch) {
        List<String> noPatchLines = new ArrayList<String>(target.parsedLines.keySet());
        noPatchLines.removeAll(res.keyMatchLines);
        List<String> noTargetLines = new ArrayList<String>(patch.parsedLines.keySet());
        noTargetLines.removeAll(res.keyMatchLines);

        if (noPatchLines.size() > 0) {
            System.out
                .println("\n\nLines in target file which have not matching line from patch file:");
            for (String s : noPatchLines) {
                System.out.println("\t" + s);
            }
        }

        if (noTargetLines.size() > 0) {
            System.out
                .println("\n\nLines in patch file which have not matching line from target file:");
            for (String s : noTargetLines) {
                System.out.println("\t" + s);
            }
        }

        System.out.println("\n\n"
            + (res.keyMatchLines.size() - res.dummy)
            + " lines were updated from patch file, "
            + res.dummy
            + " dummy update were ignored and "
            + noPatchLines.size()
            + " lines have not matching line from patch file");
    }

    public static List<String> getLines(String file) {
        InputStream fis = null;
        String strLine = null;
        List<String> lines = new ArrayList<String>();

        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);

            while ((strLine = br.readLine()) != null) {
                if (strLine.trim().length() > 0)
                    lines.add(strLine);
            }

            if (lines.size() < 1)
                throw new RuntimeException("Empty file: " + file);

            return lines;

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private static void storeMergedCsv(String resultFile, String resultSep, List<String[]> res) {
        if (resultSep == null || resultSep.length() < 1)
            throw new IllegalArgumentException(
                "Separator must be one or more than one character long!");

        PrintWriter pf = null;

        try {
            pf = new PrintWriter(resultFile);
            for (String[] line : res) {
                for (String s : line) {
                    pf.print(s);
                    pf.print(resultSep);
                }
                pf.println();
            }
            pf.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (pf != null) {
                pf.close();
            }
        }
    }

    // ******************* data units

    public static class MergeResult {

        public int dummy = 0;

        /**
         * matching keys
         */
        final public List<String> keyMatchLines;
        final public List<String[]> resultLines;

        public MergeResult(List<String> keyMatchLines, List<String[]> resultLines) {
            this.keyMatchLines = keyMatchLines;
            this.resultLines = resultLines;
        }
    }

    public static class ParsedCsv {
        final public int columnCount;
        final public TreeMap<String, String[]> parsedLines;

        public ParsedCsv(int columnCount, TreeMap<String, String[]> parsedLines) {
            this.columnCount = columnCount;
            this.parsedLines = parsedLines;
        }
    }

    public static String[] csvSeparatorSplitLogic(String csvLine, String sep) {
        List<String> splitted = new ArrayList<>();
        boolean betweenQuot = false;
        StringBuffer part = new StringBuffer();
        StringBuffer csvLineBuf = new StringBuffer(csvLine);
        while (csvLineBuf.length() > 0) {
            if (csvLineBuf.toString().startsWith("\"")) {
                betweenQuot = !betweenQuot;
                csvLineBuf.delete(0, 1);
            } else if (csvLineBuf.toString().startsWith(sep) && !betweenQuot) {
                splitted.add(part.toString());
                part = new StringBuffer();
                csvLineBuf.delete(0, sep.length());
            } else if (csvLineBuf.toString().startsWith(System.lineSeparator())) {
                splitted.add(part.toString());
                part = new StringBuffer();
                csvLineBuf.delete(0, System.lineSeparator().length());
            } else {
                part.append(csvLineBuf.charAt(0));
                csvLineBuf.delete(0, 1);
            }
        }
        if (part.length() > 0)
            splitted.add(part.toString());
        return splitted.toArray(new String[splitted.size()]);

    }
}
