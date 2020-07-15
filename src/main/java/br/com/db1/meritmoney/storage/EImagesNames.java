package br.com.db1.meritmoney.storage;

public enum EImagesNames {
    PROFILE_PHOTO("profile_photo"),
    TEAM_PHOTO("team_main_photo");

    String desc;

    EImagesNames(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
