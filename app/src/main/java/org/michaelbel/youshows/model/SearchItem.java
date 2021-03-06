package org.michaelbel.youshows.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Date: 18 MAY 2018
 * Time: 17:59 MSK
 *
 * @author Michael Bel
 */

@RealmClass
public class SearchItem extends RealmObject {

    public String query;

    public String date;

    public boolean voice;
}