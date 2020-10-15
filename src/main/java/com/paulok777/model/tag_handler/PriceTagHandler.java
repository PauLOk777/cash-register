package com.paulok777.model.tag_handler;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class PriceTagHandler extends BodyTagSupport {
    @Override
    public int doEndTag() {
        try {
            JspWriter writer = pageContext.getOut();
            String price = getBodyContent().getString();
            switch (price.length()) {
                case 1:
                    writer.print("0.0" + price);
                    break;
                case 2:
                    writer.print("0." + price);
                    break;
                default:
                    String billPart = price.substring(0, price.length() - 2);
                    String coinPart = price.substring(price.length() - 2, price.length());
                    writer.print(billPart + "." + coinPart);
            }
        } catch (Exception e) {
            System.out.println("error");
        }
        return BodyTagSupport.SKIP_BODY;
    }
}
