package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Article;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ArticlesController {

    public static ArrayList<Article> getAllArticles(){
        ArrayList<Article> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Article> articles = realm.where(Article.class).findAll();
        for (Article article:articles) {
            result.add(article);
        }
        realm.close();
        return result;
    }

    public static ArrayList<String> getArticlesLabel(ArrayList<Article> articles){
        ArrayList<String> articlesName = new ArrayList<>();
        for (Article article:articles)
            articlesName.add(article.getArt_lib());
        return articlesName;
    }

    public static ArrayList<String> getArticlesId(ArrayList<Article> articles){
        ArrayList<String> articlesName = new ArrayList<>();
        for (Article article:articles)
            articlesName.add(article.getArt_code());
        return articlesName;
    }

    public static ArrayList<String> getArticlesFamilies(){
        ArrayList<String> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Article> articles = realm.where(Article.class).distinct("far_lib").findAll();
        for (Article article:articles) {
            if (!article.getFar_lib().equals(""))
            result.add(article.getFar_lib());
        }
        realm.close();
        return result;
    }

    public static ArrayList<Article> getArticlebyFamily(ArrayList<Article> articles,String family){
        ArrayList<Article> result = new ArrayList<>();
        for (Article article:articles)
            if (article.getFar_lib().equals(family))
                result.add(article);
        return result;
    }

    public static ArrayList<RealmObject> getArticlesSubList(ArrayList<Article> articles, ArrayList<Integer> which){
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i=0;i<which.size();i++)
            filters.add(articles.get(which.get(i)));
        return filters;
    }
}
