package com.inepex.ineForm.server.tablerenderer.excel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
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
import com.inepex.ineom.shared.descriptor.fdesc.DoubleFDesc;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;
import com.inepex.ineom.shared.descriptor.fdesc.LongFDesc;
import com.inepex.ineom.shared.descriptorstore.DescriptorStore;
import com.inepex.ineom.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.VerticalAlignment;

public class ExcelRendererV2 extends TableRenderer {

    public static interface ExcelRendererV2Factory {
        public ExcelRendererV2 create(
            @Assisted("od") String objectDescName,
            @Assisted("td") @Nullable String tableRDescName,
            @Assisted Sheet sheet);
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

    protected HashMap<String, CellStyle> definedStyles;
    private Map<String, ExcelCellDisplayer> cellDisplayers = new HashMap<>();
    private AssistedObjectHandlerFactory aoHandlerFactory;
    private CellStyle[] columnCellStyles;

    @Inject
    public ExcelRendererV2(
        DescriptorStore descStore,
        AssistedObjectHandlerFactory aoHandlerFactory,
        @Assisted("od") String objectDescName,
        @Assisted("td") @Nullable String tableRDescName,
        @Assisted Sheet sheet,
        TableFieldRenderer fieldRenderer) {
        super(descStore, objectDescName, tableRDescName, fieldRenderer);
        this.aoHandlerFactory = aoHandlerFactory;
        this.sheet = sheet;
        startRowNr = startCellNr = 0;
        setRenderLastFieldEnd(true);
        initColumnCellStyles();
    }

    private void initColumnCellStyles() {
        columnCellStyles = new CellStyle[tableRDesc.getRootNode().getChildren().size() - getHiddenColumns().size()];
        int colWithHidden = 0;
        for (int col = 0; col < tableRDesc.getRootNode().getChildren().size(); col++) {
            Node<TableRDescBase> columnNode = tableRDesc.getRootNode().getChildren().get(col);
            ColRDesc colRenderDesc = (ColRDesc) columnNode.getNodeElement();

            if (getHiddenColumns().contains(columnNode.getNodeId())) {
                continue;
            }

            FDesc fdesc = getFieldDescForColumn(columnNode);
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

            CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
            if (fdesc instanceof LongFDesc) {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat(
                        IFConsts.NumberFormat.defaultExcelWholeNumberFormat));
            } else if (fdesc instanceof DoubleFDesc) {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat(
                        IFConsts.NumberFormat.defaultExcelNumberFormat));
            } else {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
            }

            String dataFormatOverride = null;
            if (colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT) != null) {
                dataFormatOverride = colRenderDesc.getPropValue(ColRDesc.EXCEL_NUMBERFORMAT);
            }
            if (dataFormatOverride != null) {
                cellStyle
                    .setDataFormat(createHelper.createDataFormat().getFormat(dataFormatOverride));
            }

            columnCellStyles[colWithHidden++] = cellStyle;
        }

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
        // not used, see setValue
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
        renderStart();
        if (renderHeader)
            renderHeader();

        for (AssistedObject kvo : kvos) {
            renderLineStart();
            int colWithHidden = 0;
            for (int col = 0; col < tableRDesc.getRootNode().getChildren().size(); col++) {
                Node<TableRDescBase> columnNode = tableRDesc.getRootNode().getChildren().get(col);
                if (getHiddenColumns().contains(columnNode.getNodeId())) {
                    continue;
                }
                if (!(IneFormProperties.showIds || tableRDesc.hasProp(FormRDescBase.prop_showIDs))
                    && IFConsts.KEY_ID.equals(columnNode.getNodeId()))
                    continue;
                renderFieldStart();
                String key = getKey(columnNode);
                AssistedObjectHandler kvoOrRelatedKvoHandler = getKvoOrRelatedKvoHandler(
                    columnNode,
                    kvo);
                actualCell.setCellStyle(columnCellStyles[colWithHidden++]);
                setValue(key, kvoOrRelatedKvoHandler, columnNode);
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

    private String getKey(Node<TableRDescBase> columnNode) {
        String key = columnNode.getNodeId();
        key = SharedUtil.deepestKey(key);
        return key;

    }

    private AssistedObjectHandler getKvoOrRelatedKvoHandler(
        Node<TableRDescBase> columnNode,
        AssistedObject kvo) {
        String key = columnNode.getNodeId();
        AssistedObjectHandler kvoOrRelatedKvoChecker = aoHandlerFactory
            .createHandler(kvo)
            .getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
        if (SharedUtil.isMultilevelKey(key)) {
            kvoOrRelatedKvoChecker = kvoOrRelatedKvoChecker
                .getRelatedKVOMultiLevel(SharedUtil.listFromDotSeparated(key));
        }
        return kvoOrRelatedKvoChecker;
    }

    private void setValue(
        String key,
        AssistedObjectHandler kvoOrRelatedKvoChecker,
        Node<TableRDescBase> columnNode) {
        ColRDesc colRenderDesc = (ColRDesc) columnNode.getNodeElement();
        fieldRenderer
            .setObjectAndDescriptor(kvoOrRelatedKvoChecker.getAssistedObject(), tableRDesc);
        if (cellDisplayers.containsKey(key)) {
            cellDisplayers
                .get(key)
                .fillCell(actualCell, kvoOrRelatedKvoChecker, key, colRenderDesc);
        } else if (fieldRenderer.containsCustomizer(key)) {
            actualCell.setCellValue(fieldRenderer.getFieldByCustomizer(key));
        } else {
            FDesc fdesc = getFieldDescForColumn(columnNode);
            if (fdesc instanceof LongFDesc) {
                Long value = kvoOrRelatedKvoChecker.getLong(key);
                if (value != null) {
                    actualCell.setCellValue(value);
                }
            } else if (fdesc instanceof DoubleFDesc) {
                Double value = kvoOrRelatedKvoChecker.getDouble(key);
                if (value != null) {
                    actualCell.setCellValue(value);
                }
            } else {
                actualCell.setCellValue(fieldRenderer.getField(key));
            }
        }
    }

    public Map<String, ExcelCellDisplayer> getCellDisplayers() {
        return cellDisplayers;
    }

    public CellStyle[] getColumnCellStyles() {
        return columnCellStyles;
    }

    @Override
    public void hideColumn(String column) {
        super.hideColumn(column);
        initColumnCellStyles();
    }
}
