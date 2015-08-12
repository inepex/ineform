package com.inepex.ineForm.client.general;

import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Float;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Image;
import com.inepex.ineForm.client.IneFormProperties;
import com.inepex.ineFrame.client.misc.HandlerAwareComposite;
import com.inepex.ineFrame.client.widget.ClickableFlowPanel;

public class IneCheckBox extends HandlerAwareComposite
    implements
    HasValue<Boolean>,
    HasText,
    HasHTML,
    HasEnabled {

    private final FlowPanel mainPanel = new FlowPanel();
    private final Image icon = new Image();
    private final ClickableFlowPanel checkPanel = new ClickableFlowPanel();
    private final HTML textWidget = new HTML("");
    private Boolean checked = false;
    private boolean enabled = true;

    public IneCheckBox() {
        initWidget(mainPanel);
        createUI();
    }

    public IneCheckBox(String text) {
        this();
        setText(text);
    }

    private void createUI() {
        mainPanel.add(icon);
        icon.setVisible(false);
        mainPanel.add(checkPanel);
        mainPanel.add(textWidget);

        if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
            textWidget.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxText_old());
            checkPanel.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBox_old());

        } else {
            textWidget.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxText());
            checkPanel.setStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBox());
        }
        checkPanel.getElement().getStyle().setPosition(Position.STATIC);
        mainPanel.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
        icon.getElement().getStyle().setFloat(Float.LEFT);
        icon.getElement().getStyle().setPaddingRight(6.0, Unit.PX);
    }

    private void correctCheckboxStyle() {
        if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
            if (checked) {
                checkPanel.addStyleName(GeneralRes.INST
                    .get()
                    .GeneralStyle()
                    .ineCheckBoxActive_old());
            } else {
                checkPanel.removeStyleName(GeneralRes.INST
                    .get()
                    .GeneralStyle()
                    .ineCheckBoxActive_old());
            }
        } else {
            if (checked) {
                checkPanel.addStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxActive());
            } else {
                checkPanel
                    .removeStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxActive());
            }
        }
    }

    @Override
    public void setValue(Boolean value) {
        checked = (value != null && value);
        correctCheckboxStyle();
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return checkPanel.addClickHandler(handler);
    }

    public HandlerRegistration addLabelClickHandler(ClickHandler clickHandler) {
        return textWidget.addClickHandler(clickHandler);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
        return addHandler(handler, ValueChangeEvent.getType());
    }

    @Override
    public Boolean getValue() {
        return checked;
    }

    @Override
    public void setValue(Boolean value, boolean fireEvents) {
        checked = (value != null && value);

        if (fireEvents && value != null) {
            ValueChangeEvent.fire(IneCheckBox.this, value);
        }

        correctCheckboxStyle();
    }

    @Override
    protected void onAttach() {
        super.onAttach();

        registerHandler(checkPanel.addClickHandler(clickHandler()));
        registerHandler(icon.addClickHandler(clickHandler()));
    }

    private ClickHandler clickHandler() {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (!enabled)
                    return;

                checked = !checked;
                ValueChangeEvent.fire(IneCheckBox.this, checked);
                correctCheckboxStyle();
            }
        };
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (IneFormProperties.IN_OLD_STYLE_COMPATIBILITY_MODE) {
            if (enabled)
                textWidget.removeStyleName(GeneralRes.INST
                    .get()
                    .GeneralStyle()
                    .ineCheckBoxDisabled_old());
            else
                textWidget.addStyleName(GeneralRes.INST
                    .get()
                    .GeneralStyle()
                    .ineCheckBoxDisabled_old());
        } else {
            if (enabled)
                textWidget.removeStyleName(GeneralRes.INST
                    .get()
                    .GeneralStyle()
                    .ineCheckBoxDisabled());
            else
                textWidget.addStyleName(GeneralRes.INST.get().GeneralStyle().ineCheckBoxDisabled());
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getHTML() {
        return textWidget.getHTML();
    }

    @Override
    public void setHTML(String arg0) {
        textWidget.setHTML(arg0);
    }

    @Override
    public String getText() {
        return textWidget.getText();
    }

    @Override
    public void setText(String arg0) {
        textWidget.setText(arg0);
    }

    public HTML getTextWidget() {
        return textWidget;
    }

    public void setIconUrl(String iconUrl) {
        icon.setUrl(iconUrl);
        icon.setVisible(true);
    }
}
