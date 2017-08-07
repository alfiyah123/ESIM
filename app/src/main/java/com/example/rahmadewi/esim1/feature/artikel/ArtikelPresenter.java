package com.example.rahmadewi.esim1.feature.artikel;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.rahmadewi.esim1.base.ui.BasePresenter;
import com.example.rahmadewi.esim1.database.CrudMateri;
import com.example.rahmadewi.esim1.feature.image.ImageActivity;
import com.example.rahmadewi.esim1.models.materi.ResultItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.File;

class ArtikelPresenter extends BasePresenter<ArtikelView> {
    ArtikelPresenter(ArtikelView view){
        super.attachView(view);
    }

    void getData(String sub_topik){
        ResultItem hasil;
        CrudMateri materi = new CrudMateri();
        hasil = materi.getDataBySubTopik(sub_topik);

        view.setTexView(hasil.getSubTopik());

        String html = hasil.getMateri();

        html = html.replace("&lt;","<");
        html = html.replace("&gt;",">");
        html = html.replace("&#13;","\r");
        html = html.replace("&#10;","\n");

        Document doc = Jsoup.parse(html);

        parseHtml(doc);
    }

    private void parseHtml(Document doc){
        int i = 0;
        Elements a = doc.getAllElements();

        for (Element element : a) {
            Tag tag = element.tag();
            if (tag.getName().matches("h[1-6]{1}")) {
                String heading = element.select(tag.getName()).text();
                view.parseH(heading);
            }
            if (tag.getName().equalsIgnoreCase("p")) {
                element.select("img").remove();
                String body = element.html();
                view.parseP(body);
            }
            if (tag.getName().equalsIgnoreCase("ol")) {
                StringBuilder sb = new StringBuilder();
                final String typeAttr = element.attr("type");
                char type = typeAttr.isEmpty() ? '1' : typeAttr.charAt(0);
                for( Element sub : element.children() )
                {
                    switch( sub.tagName() )
                    {
                        case "li": // Listitem, format and append
                            sb.append(type++).append(". ").append(sub.ownText()).append("\n");
                            break;
                        case "ol":

                        default:
                            System.err.println("Not implemented tag: " + sub.tagName());
                    }
                }
                view.parseOl(sb.toString());
            }
            if (tag.getName().equalsIgnoreCase("ul")) {
                String ul = element.select(tag.getName().toString()).outerHtml();
                view.parseUl(ul);
            }
            if (tag.getName().equalsIgnoreCase("img")) {
                int k = 0;
                String nama = "large.jpg";
                String url = element.select("img").attr("src");
                String namaPanjang = url.substring(url.lastIndexOf("/") + 1);

                if (namaPanjang.indexOf('?') > -1) { // Check if there is more than one word.
                    nama =  namaPanjang.substring(0, namaPanjang.indexOf('?')); // Extract first word.
                }

                String path = Environment.getExternalStorageDirectory()+ "/SIM_IMAGES/"+nama;

                File imgFile = new File(path);
                if(imgFile.exists())
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    view.parseImg(myBitmap, nama);
                }
            }
        }
    }

    void moveToIntent(Activity activity, String image){
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra("fileName", image);
        view.moveIntent(intent);
    }
}
