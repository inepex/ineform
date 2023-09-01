package com.inepex.ineForm.server.tablerenderer.excel;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineForm.shared.Nullable;
import com.inepex.ineForm.shared.descriptorext.ColRDesc;
import com.inepex.ineForm.shared.descriptorext.FormRDescBase;
import com.inepex.ineForm.shared.descriptorext.TableRDescBase;
import com.inepex.ineForm.shared.render.TableFieldRenderer;
import com.inepex.ineForm.shared.tablerender.TableRenderer;
import com.inepex.ineom.shared.AssistedObjectHandler;
import com.inepex.ineom.shared.AssistedObjectHandlerFactory;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.assistedobject.AssistedObject;
import com.inepex.ineom.shared.descriptor.Node;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelRenderer extends TableRenderer {

    public static interface ExcelRendererFactory {
        public ExcelRenderer create(
            @Assisted("od") String objectDescName,
            @Assisted("td") @Nullable String tableRDescName,
            @Assisted Sheet sheet,
            @Assisted boolean setCellTypes);
    }

    public static interface ExcelCellDisplayer {

        public void fillCell(
            Cell cell,
            AssistedObjectHandler rowKvo,
            String fieldId,
            ColRDesc colRDesc);

    }

    protected final Sheet sheet;
    protected int startRowNr;
    protected int actualRowNr;
    protected Row actualRow;
    protected int startCellNr;
    protected int actualCellNr;
    protected Cell actualCell;
    protected boolean setCellTypes;

    protected HashMap<String, CellStyle> definedStyles;
    private Map<String, ExcelCellDisplayer> cellDisplayers = new HashMap<>();

    @Inject
    public ExcelRenderer(
        @Assisted("od") String objectDescName,
        @Assisted("td") @Nullable String tableRDescName,
        @Assisted Sheet sheet,
        @Assisted boolean setCellTypes,
        DescriptorStore descStore,
        TableFieldRenderer fieldRenderer) {
        super(descStore, objectDescName, tableRDescName, fieldRenderer);
        this.sheet = sheet;
        startRowNr = startCellNr = 0;
        setRenderLastFieldEnd(true);
        this.setCellTypes = setCellTypes;
    }

    public void setStartRowNr(int rowNr) {
        this.startRowNr = rowNr;
    }

    public void setStartCellNr(int cellNr) {
        this.startCellNr = cellNr;
    }

    @Override
    protected void renderStart() {
        actualRowNr = startRowNr;
    }

    @Override
    protected void renderEnd() {
        for (int i = startCellNr; i <= actualCellNr; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    @Override
    protected void renderLineStart() {
        actualRow = sheet.createRow((short) actualRowNr);
        actualCellNr = startCellNr;
    }

    @Override
    protected void renderLineEnd() {
        actualRowNr++;
    }

    @Override
    protected void renderFieldStart() {
        actualCell = actualRow.createCell(actualCellNr);

    }

    @Override
    protected void renderField(String content, ColRDesc colRdesc) {
        actualCell.setCellValue(content);
    }

    @Override
    protected void renderFieldEnd() {
        actualCellNr++;
    }

    @Override
    protected void renderHeaderStart() {
        renderLineStart();
    }

    @Override
    protected void renderHeaderEnd() {
        renderLineEnd();
    }

    @Override
    protected void renderHeaderField(String key, String content) {
        actualCell.setCellValue(content);
    }

    @Override
    protected void renderHeaderFieldEnd() {
        renderFieldEnd();
    }

    public int getActualRowNr() {
        return actualRowNr;
    }

    @Override
    protected void renderHeaderFieldStart(ColRDesc colRDesc, FDesc fDesc) {
        renderFieldStart();
    }

    @Override
    public String render(List<? extends AssistedObject> kvos) {
        if (!setCellTypes) {
            return super.render(kvos);
        }
        AssistedObjectHandlerFactory factory = new AssistedObjectHandlerFactory(descStore);
        renderStart();
        if (renderHeader)
            renderHeader();

        for (AssistedObject kvo : kvos) {
            renderLineStart();
            for (Node<TableRDescBase> columnNode : tableRDesc.getRootNode().getChildren()) {
                renderFieldStart();
                ColRDesc colRenderDesc = (ColRDesc) columnNode.getNodeElement();
                if (!(IneFormProperties.showIds || tableRDesc.hasProp(FormRDescBase.prop_showIDs))
                    && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
                    continue;
                String key = columnNode.getNodeId();
                AssistedObjectHandler kvoOrRelatedKvoChecker = factory
                    .createHandler(kvo)
                    .getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
                if (SharedUtil.isMultilevelKey(key)) {
                    kvoOrRelatedKvoChecker = kvoOrRelatedKvoChecker
                        .getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
                }
                key = SharedUtil.deepestKey(key);

                applyCellFormat(colRenderDesc);

                setValue(key, kvoOrRelatedKvoChecker, colRenderDesc);

                cellValueSet(key, kvoOrRelatedKvoChecker);

                if (renderLastFieldEnd || !columnNode.equals(
                    tableRDesc.getRootNode().getChildren().get(
                        tableRDesc.getRootNode().getChildren().size() - 1)))
                    renderFieldEnd();

            }
            renderLineEnd();
        }

        renderEnd();
        return "";
    }

    private void applyCellFormat(ColRDesc colRenderDesc) {
        if (colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT) != null) {
            setDataFormatForActualCell(colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT));
        } else if (colRenderDesc.getPropValue(ColRDesc.EXCEL_DATETIMEFORMAT) != null) {
            setDataFormatForActualCell(colRenderDesc.getPropValue(ColRDesc.EXCEL_DATETIMEFORMAT));
        }
    }

    private void setValue(
        String key,
        AssistedObjectHandler kvoOrRelatedKvoChecker,
        ColRDesc colRenderDesc) {
        fieldRenderer
            .setObjectAndDescriptor(kvoOrRelatedKvoChecker.getAssistedObject(), tableRDesc);
        if (cellDisplayers.containsKey(key)) {
            cellDisplayers
                .get(key)
                .fillCell(actualCell, kvoOrRelatedKvoChecker, key, colRenderDesc);
        } else if (fieldRenderer.containsCustomizer(key)) {
            actualCell.setCellValue(fieldRenderer.getFieldByCustomizer(key));
        } else {
            if (colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT) != null) {
                Double num = kvoOrRelatedKvoChecker.getDouble(key);
                if (num != null) {
                    actualCell.setCellValue(num);
                }
            } else if (colRenderDesc.getPropValue(ColRDesc.EXCEL_DATETIMEFORMAT) != null) {
                Long date = kvoOrRelatedKvoChecker.getLong(key);
                if (date != null) {
                    actualCell.setCellValue(getGMTCalendar(date));
                }
            } else {
                actualCell.setCellValue(fieldRenderer.getField(key));
            }

        }
    }

    protected Calendar getGMTCalendar(long timeInMillis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeInMillis);
        // c.setTimeZone(TimeZone.getTimeZone("GMT"));
        return c;
    }

    protected void setDataFormatForActualCell(String format) {
        actualCell.setCellStyle(getOrCreateCellStyle(format));
    }

    public CellStyle getOrCreateCellStyle(String format) {
        if (definedStyles == null) {
            definedStyles = new HashMap<String, CellStyle>();
        }
        if (!definedStyles.containsKey(format)) {
            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(format));
            definedStyles.put(format, cellStyle);
        }
        return definedStyles.get(format);
    }

    protected void cellValueSet(String key, AssistedObjectHandler rowKvo) {}

    public Map<String, ExcelCellDisplayer> getCellDisplayers() {
        return cellDisplayers;
    }
}
