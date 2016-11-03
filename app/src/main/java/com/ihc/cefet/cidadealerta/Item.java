package com.ihc.cefet.cidadealerta;

import android.view.View;

import java.util.ArrayList;

/**
 * Simple POJO model for example
 */
public class Item {

    private String category;
    private String status;
    private int statusCod;
    private String address;
    private String description;
    private int favoriteCount;
    private String date;
    private int image;
    private String time;
    private String dateShort;
    private String user;
    private int avatar;
    private String lat;
    private String lng;
    private boolean userCreated;
    private boolean deleted;

    private boolean favorited;

    private View.OnClickListener requestBtnClickListener;

    public Item() {
    }

    public Item(String category, String status, String address, String description, int favoriteCount, String date, int statusCod,
                int image, String time, String dateShort, String user, int avatar, String lat, String lng, boolean userCreated) {
        this.category = category;
        this.status = status;
        this.address = address;
        this.description = description;
        this.favoriteCount = favoriteCount;
        this.date = date;
        this.statusCod = statusCod;
        this.image = image;
        this.time = time;
        this.dateShort = dateShort;
        this.user = user;
        this.avatar = avatar;
        this.lat = lat;
        this.lng = lng;
        this.userCreated = userCreated;
        this.deleted = false;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatusCod() {
        return statusCod;
    }

    public void setStatusCod(int statusCod) {
        this.statusCod = statusCod;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDateShort() {
        return dateShort;
    }

    public void setDateShort(String dateShort) {
        this.dateShort = dateShort;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public boolean isUserCreated() {
        return userCreated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void setUserCreated(boolean userCreated) {
        this.userCreated = userCreated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (favoriteCount != item.favoriteCount) return false;
        if (category != null ? !category.equals(item.category) : item.category != null) return false;
        if (status != null ? !status.equals(item.status) : item.status != null)
            return false;
        if (address != null ? !address.equals(item.address) : item.address != null)
            return false;
        if (description != null ? !description.equals(item.description) : item.description != null)
            return false;
        return (date != null ? !date.equals(item.date) : item.date != null);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + favoriteCount;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("Buraco na Rua", "Reconhecido", "Av. Amazonas 1305 - Nova Gameleira", "", 10,
                "17:30 - 22 de SET de 2016", 1, R.drawable.pothole, "17:30", "22 de SET de 2016", "Fernando Silva", R.drawable.ic_user2, "-19.938591","-43.999396", false));
        items.add(new Item("Placa caída", "Aberto", "Rua Domingos Moutinho Teixeira, 84 - Palmares", "Placa de rua sem saída está tombada há três meses", 8,
                "10:30 - 25 de SET de 2016", 0, -1, "10:30", "25 de SET de 2016", "Joana Rosa", R.drawable.ic_user3, "-19.8709149","-43.9357973", true));
        items.add(new Item("Poste sem luz", "Fechado", "Rua Juvenal de Melo Senra, 663 - Belvedere", "Vários postes da praça estão sem luz", 20,
                "17:15 - 02 de JUL de 2016", 2, -1, "17:15", "02 de JUL de 2016", "João Teixeira", -1, "-19.9753624","-43.9497407", false));
        items.add(new Item("Vandalismo", "Reconhecido", "Rua Mario Martins, 358 - Pompéia", "Muro pichado", 2,
                "08:00 - 16 de AGO de 2016", 1, -1, "08:00", "16 de AGO de 2016", "Sonia Silva", -1, "-19.9111963","-43.9035173", false));
        items.add(new Item("Vandalismo", "Aberto", "Rua Glocinia, 146A - Santo Andre", "Pichação", 7,
                "22:00 - 19 de AGO de 2016", 0, -1, "22:00", "19 de AGO de 2016", "Marcelo Ribeiro", R.drawable.ic_user1, "-19.9054383","-43.9615281", false));
        items.add(new Item("Buraco na rua", "Fechado", "Rua Pará de Minas, 455 - Padre Eustaquio", "A chuva esburacou o chão semana passada", 5,
                "14:05 - 21 de JUL de 2016", 2, -1, "14:05", "21 de JUL de 2016", "Maria Eugênia Souza", R.drawable.ic_user5, "-19.9147484","-43.9875966", false));
        return items;

    }

}