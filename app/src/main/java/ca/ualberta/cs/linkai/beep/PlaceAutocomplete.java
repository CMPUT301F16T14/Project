package ca.ualberta.cs.linkai.beep;

/**
 * Created by Aries on 20/11/2016.
 * @author jinzhu
 * 
 * This class is used to store the location information when the user points two dots on the map.
 * 
 */

public class PlaceAutocomplete {

    /* variables for map place */
    public CharSequence placeId;
    public CharSequence description;
    
    /* class contractor */
    PlaceAutocomplete(CharSequence placeId, CharSequence description) {
        this.placeId = placeId;
        this.description = description;
    }

    public CharSequence getPlaceId() {
        return placeId;
    }

    public void setPlaceId(CharSequence placeId) {
        this.placeId = placeId;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description.toString();
    }
}
