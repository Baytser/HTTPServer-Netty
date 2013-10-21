/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pages;

/**
 *
 * 
 */
public class PageHello extends Page{
    
    String page = "<html><head><title>" + "Hello" + "</title></head>" + brline
                + "<body>" + brline
                + "<h1>Hello Word</h1>" + brline
                + "</body>" + brline
                + "</html>" + brline;

    @Override
    public String getPage() {
        return page;
    }
}
