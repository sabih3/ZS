package netaq.com.zayedsons.network.model;

/**
 * Created by Sabih on 01/03/2017.
 */
public class ClickATellMessageObject {

    String text;
    String[] to;
    String unicode;
    String from;

    public ClickATellMessageObject(String text, String[] to, String unicode, String from) {
        this.text = text;
        this.to = to;
        this.unicode = unicode;
        this.from = from;
    }

    public ClickATellMessageObject(String text, String[] to, String from) {
        this.text = text;
        this.to = to;
        this.from = from;
    }

    public ClickATellMessageObject(String text, String[] to) {
        this.text = text;
        this.to = to;
        this.from = from;
    }
}
