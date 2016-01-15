package com.inepex.ineForm.client.general;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasEnabled;
import com.google.gwt.user.client.ui.HasHTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class IneButton extends Composite implements IsWidget, HasEnabled, HasText, HasHTML {

    public static enum Color {

        /**
         * to commit an action that effects data changes or changes in the sate
         * of user or view <br>
         * <b>green</b>
         */
        GREEN,

        /**
         * to perform an action that creates a new instance of something <br>
         * <b>blue</b>
         */
        BLUE,

        /**
         * to cancel an action or form <br>
         * <b>light gray</b>
         */
        GRAY,

        /**
         * to modify the properties of action (what is useless without saving) <br>
         * <b>transparent</b>
         */
        TRANSPARENT;
    }

    public static enum Position {
        LEFT,
        RIGHT,
        CENTER
    }

    private static IFButtonUiBinder uiBinder = GWT.create(IFButtonUiBinder.class);

    interface IFButtonUiBinder extends UiBinder<Widget, IneButton> {}

    interface ButtonStyles extends CssResource {
        String greenColors();

        String blueColors();

        String grayColors();

        String transparentColors();

        String disabled();

        String normalSize();

        String oneCharSize();
    }

    @UiField
    Button button;

    @UiField
    ButtonStyles style;

    public IneButton() {
        this(Color.GRAY);
    }

    public IneButton(String text) {
        this(Color.GRAY, text);
    }

    public IneButton(Color type, String text) {
        this(type);
        button.setText(text);
    }

    public IneButton(Color type) {
        initWidget(uiBinder.createAndBindUi(this));
        setColor(type);
    }

    public void setColor(Color type) {
        switch (type) {
            case GREEN:
                button.addStyleName(style.greenColors());
                button.addStyleName(style.normalSize());
                break;

            case BLUE:
                button.addStyleName(style.blueColors());
                button.addStyleName(style.normalSize());
                break;

            case TRANSPARENT:
                button.addStyleName(style.transparentColors());
                button.addStyleName(style.oneCharSize());
                break;

            case GRAY:
            default:
                button.addStyleName(style.grayColors());
                button.addStyleName(style.normalSize());
                break;
        }
    }

    public void setIcon(ImageResource icon, Position position) {
        button
            .getElement()
            .getStyle()
            .setProperty("backgroundImage", "url('" + icon.getSafeUri().asString() + "')");

        if (position != Position.CENTER) {
            button
                .getElement()
                .getStyle()
                .setProperty("backgroundPosition", position == Position.LEFT ? "5%" : "95%");
        } else {
            button.getElement().getStyle().setProperty("backgroundPosition", "center");
        }

    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    @Override
    public String getText() {
        return button.getText();
    }

    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return button.addClickHandler(handler);
    }

    @Override
    public boolean isEnabled() {
        return button.isEnabled();
    }

    @Override
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
        if (enabled)
            button.removeStyleName(style.disabled());
        else
            button.addStyleName(style.disabled());
    }

    @Override
    public String getHTML() {
        return button.getHTML();
    }

    @Override
    public void setHTML(String html) {
        button.setHTML(html);
    }

    public IneButton withRightMargin() {
        button.getElement().getStyle().setMarginRight(12, Unit.PX);
        return this;
    }
}
