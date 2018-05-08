package d2si.apps.planetedashboard.database.controller;

import java.util.ArrayList;

import d2si.apps.planetedashboard.database.data.Article;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Controller of articles
 * <p>
 * This controller is used to control articles from database
 *
 * @author younessennadj
 */

public class ArticlesController {

    /**
     * Method that get all articles from database
     *
     * @return all articles from database
     */
    public static ArrayList<Article> getAllArticles() {
        ArrayList<Article> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Article> articles = realm.where(Article.class).findAll();
        for (Article article : articles) {
            result.add(article);
        }
        realm.close();
        return result;
    }

    /**
     * Method that get articles labels from database
     *
     * @param articles list of articles
     * @return list of articles labels
     */
    public static ArrayList<String> getArticlesLabel(ArrayList<Article> articles) {
        ArrayList<String> articlesName = new ArrayList<>();
        for (Article article : articles)
            articlesName.add(article.getArt_lib());
        return articlesName;
    }

    /**
     * Method that get articles Ids from database
     *
     * @param articles list of articles
     * @return list of articles Ids
     */
    public static ArrayList<String> getArticlesId(ArrayList<Article> articles) {
        ArrayList<String> articlesName = new ArrayList<>();
        for (Article article : articles)
            articlesName.add(article.getArt_code());
        return articlesName;
    }

    /**
     * Method that get articles families from database
     *
     * @return list of articles families
     */
    public static ArrayList<String> getArticlesFamilies() {
        ArrayList<String> result = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Article> articles = realm.where(Article.class).distinct("far_lib").findAll();
        for (Article article : articles) {
            if (!article.getFar_lib().equals(""))
                result.add(article.getFar_lib());
        }
        realm.close();
        return result;
    }

    /**
     * Method that get articles by family from database
     *
     * @param articles list of articles
     * @param family   article family
     * @return list of articles associated with family
     */
    public static ArrayList<Article> getArticlebyFamily(ArrayList<Article> articles, String family) {
        ArrayList<Article> result = new ArrayList<>();
        for (Article article : articles)
            if (article.getFar_lib().equals(family))
                result.add(article);
        return result;
    }

    /**
     * Method that get sub list of articles with indexes in which
     *
     * @param articles list of articles
     * @param which    indexes of articles to return
     * @return sub list of articles with which indexes
     */
    public static ArrayList<RealmObject> getArticlesSubList(ArrayList<Article> articles, ArrayList<Integer> which) {
        ArrayList<RealmObject> filters = new ArrayList<>();
        for (int i = 0; i < which.size(); i++)
            filters.add(articles.get(which.get(i)));
        return filters;
    }
}
