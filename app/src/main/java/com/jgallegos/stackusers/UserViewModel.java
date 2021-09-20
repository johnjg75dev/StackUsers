package com.jgallegos.stackusers;

/**
 * Stores information for an individual user
 */
public class UserViewModel {

    // Store user's username
    private String mUsername;

    // Store user's bronze badge count
    private int mBronzeBadges;

    // Store user's silver badge count
    private int mSilverBadges;

    // Store user's gold badge count
    private int mGoldBadges;

    // Store user's reputation
    private int mReputation;

    // Store user's gravatar URL
    private String mGravatarURL;

    /** Constructs a new user viewmodel object with given parameters. */
    public UserViewModel(String username, int bronzeBadges, int silverBadges, int goldBadges, int reputation, String gravatarURL) {
        // Store all parameters to member variables
        this.mUsername = username;
        this.mBronzeBadges = bronzeBadges;
        this.mSilverBadges = silverBadges;
        this.mGoldBadges = goldBadges;
        this.mReputation = reputation;
        this.mGravatarURL = gravatarURL;
    }

    public String getUsername() {
        return mUsername;
    }

    public int getBronzeBadges() {
        return mBronzeBadges;
    }

    public int getSilverBadges() {
        return mSilverBadges;
    }

    public int getGoldBadges() {
        return mGoldBadges;
    }

    public int getReputation() {
        return mReputation;
    }

    public String getGravatarURL() {
        return mGravatarURL;
    }

}
