package com.inepex.ineForm.client.form.widgets.upload;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.inepex.ineForm.client.form.widgets.DenyingFormWidget;
import com.inepex.ineForm.client.general.IneButton;
import com.inepex.ineForm.client.general.IneButton.IneButtonType;
import com.inepex.ineForm.client.i18n.IneFormI18n;
import com.inepex.ineForm.shared.descriptorext.WidgetRDesc;
import com.inepex.ineom.shared.IFConsts;
import com.inepex.ineom.shared.descriptor.fdesc.FDesc;

public class FileUploadFw extends DenyingFormWidget {
    private static final String imageBaseUrl = "documents/";
    public static final String hasPreviewKey = "hasPreview";
    public static final String hasImageFinderKey = "withImageFinder";
    public static String customUploadUrl = "customUploadUrl";
    public static String customName = "name";
    public static String fileParamName = "fileParamName";

    private FlowPanel panelMain = new FlowPanel();
    private String uploadUrl = IFConsts.uploadServletUrl;
    private Image preview = new Image();
    private InlineLabel filename = new InlineLabel();
    private IneButton upload = new IneButton(IneButtonType.ACTION, IneFormI18n.imageuploadBtn());

    private ImageUploadPopup popup;

    private boolean withImageFinder = false;
    private boolean withPreview = false;

    private DefaultSearchParamProvider defaultSearchParamProvider;
    private String name = "fileupload";
    private String fileParam = "inefileupload";

    private Map<String, String> additionalPostParams = new HashMap<String, String>();

    public interface DefaultSearchParamProvider {
        public String getDefaultSearchParam();
    }

    private class FileUploadForm extends FlexTable {
        private HTML title = new HTML("<h3>" + IneFormI18n.imagefinderUploadimage() + "</h3>");
        private FormPanel panelForm = new FormPanel();
        private FlowPanel panelInsideForm = new FlowPanel();
        private FileUpload fileupload = new FileUpload();
        private IneButton btnSubmit = new IneButton(
            IneButtonType.ACTION,
            IneFormI18n.imageuploadBtn());
        private IneButton btnCancel = new IneButton(IneButtonType.CANCEL, IneFormI18n.CANCEL());
        private Label error = new Label();

        private FileUploadFw parent;

        public FileUploadForm(FileUploadFw parent) {
            this.parent = parent;
            addStyleName("FileUploadForm");
            setWidget(0, 0, title);
            setWidget(1, 0, panelForm);
            panelForm.add(fileupload);
            setWidget(2, 0, error);
            setWidget(3, 0, btnSubmit);
            setWidget(3, 1, btnCancel);
            getFlexCellFormatter().setColSpan(0, 0, 2);
            getFlexCellFormatter().setColSpan(1, 0, 2);
            getFlexCellFormatter().setColSpan(2, 0, 2);
            getCellFormatter().setHorizontalAlignment(1, 1, HasHorizontalAlignment.ALIGN_RIGHT);

            btnSubmit.addClickHandler(new ClickHandler() {

                @Override
                public void onClick(ClickEvent event) {
                    btnSubmit.setEnabled(false);
                    panelForm.submit();
                }
            });
            panelForm.setAction(uploadUrl);
            panelForm.setEncoding(FormPanel.ENCODING_MULTIPART);
            panelForm.setMethod(FormPanel.METHOD_POST);
            panelForm.setWidget(panelInsideForm);
            panelInsideForm.add(fileupload);
            panelInsideForm.add(new Hidden("name", name));

            for (Entry<String, String> entry : additionalPostParams.entrySet()) {
                panelInsideForm.add(new Hidden(entry.getKey(), entry.getValue()));
            }

            fileupload.setName(fileParam);

            panelForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
                @Override
                public void onSubmitComplete(SubmitCompleteEvent event) {
                    btnSubmit.setEnabled(true);
                    if (event.getResults().equals("<pre>-1</pre>")
                        || event.getResults().equals("-1")) {
                        error.setText(IneFormI18n.imageuploadInvalidFileFormat());
                        return;
                    }
                    if (event.getResults().equals("\n") || event.getResults().equals("")) {
                        error.setText(IneFormI18n.imageuploadError());
                        return;
                    }
                    setStringValue(event.getResults());
                    FileUploadForm.this.parent.hidePopup();
                }
            });

        }

        public HandlerRegistration addCancelClickHandler(ClickHandler handler) {
            return btnCancel.addClickHandler(handler);
        }
    }

    private class ImageUploadPopup extends DialogBox {

        private FlowPanel main = new FlowPanel();
        private FileUploadFw parent;
        private ImageFinderGoogle imagefinder;

        public ImageUploadPopup(FileUploadFw parent, boolean withImageFinder) {
            super(true);
            this.parent = parent;
            setText(IneFormI18n.imageuploadTitle());
            setAnimationEnabled(true);
            setGlassEnabled(true);
            setWidget(main);

            if (withImageFinder) {
                imagefinder = new ImageFinderGoogle(parent);
                main.add(imagefinder);
            }
            FileUploadForm fileupload = new FileUploadForm(parent);
            main.add(fileupload);

            fileupload.addCancelClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    hide();
                }
            });

        }

        public void setDefaultSearchValue(String defaultSearchValue) {
            if (withImageFinder)
                imagefinder.setDefaultSearchValue(defaultSearchValue);
        }

    }

    public FileUploadFw(
        FDesc fieldDescripor,
        WidgetRDesc wrDesc,
        boolean withImageFinder,
        boolean withPreview) {
        super(fieldDescripor);
        this.withPreview = withPreview;
        initWidget(panelMain);
        this.withImageFinder = withImageFinder;
        panelMain.addStyleName("ImageUpload");
        if (withPreview) {
            panelMain.add(preview);
            preview.setVisible(false);
        }
        panelMain.add(filename);
        filename.setText(IneFormI18n.imagefinderNoimage());
        panelMain.add(upload);
        upload.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                showPopupAndSetDefaultSearchValue();
            }
        });
        if (wrDesc.hasProp(customUploadUrl)) {
            uploadUrl = GWT.getModuleBaseURL() + wrDesc.getPropValue(customUploadUrl);
        }
        if (wrDesc.hasProp(customName)) {
            name = wrDesc.getPropValue(customName);
        }
        if (wrDesc.hasProp(fileParamName)) {
            fileParam = wrDesc.getPropValue(fileParamName);
        }
    }

    private void showPopupAndSetDefaultSearchValue() {
        if (popup == null)
            popup = new ImageUploadPopup(this, withImageFinder);

        if (defaultSearchParamProvider != null) {
            popup.setDefaultSearchValue(defaultSearchParamProvider.getDefaultSearchParam());
        }
        popup.showRelativeTo(upload);
    }

    @Override
    public String getStringValue() {
        if (!filename.getText().equals(IneFormI18n.imagefinderNoimage()))
            return filename.getText();
        else
            return null;
    }

    @Override
    public boolean handlesString() {
        return true;
    }

    @Override
    public void setStringValue(String value) {
        if (value == null || value.equals(""))
            return;
        filename.setText(value);
        if (withPreview) {
            // preview.setUrl(getUrl(value, "xs"));
            preview.setUrl(getUrl(value, ""));
            preview.setVisible(true);
        }
        upload.setText(IneFormI18n.imageuploadBtn_change());
    }

    private String getUrl(String imgRelUrl, String size) {
        String newUrl = "";
        newUrl = imageBaseUrl + imgRelUrl + size;
        return newUrl;
    }

    public void hidePopup() {
        if (popup != null) {
            popup.hide();
        }
    }

    public DefaultSearchParamProvider getDefaultSearchParamProvider() {
        return defaultSearchParamProvider;
    }

    public
        void
        setDefaultSearchParamProvider(DefaultSearchParamProvider defaultSearchParamProvider) {
        this.defaultSearchParamProvider = defaultSearchParamProvider;
    }

    public Map<String, String> getAdditionalPostParams() {
        return additionalPostParams;
    }

    @Override
    public void setEnabled(boolean enabled) {
        upload.setEnabled(enabled);
    }

}
