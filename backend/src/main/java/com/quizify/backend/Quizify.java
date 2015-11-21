package com.quizify.backend;

/**
 * Created by Ajeet Kumar Meena 32 on 15-11-2015.
 */
public class Quizify {
    static class User {
        private int userId;
        private String facebookId;
        private String gcmId;
        private String email;
        private String userName;
        private int eventPlayed;
        private int score;
        private int correctAttempted;
        private int wrongAttempted;
        private int unAttempted;

        public User(String facebookId, String gcmId, String email, String userName) {
            this.facebookId = facebookId;
            this.gcmId = gcmId;
            this.email = email;
            this.userName = userName;
        }

        public User(String facebookId, String gcmId, String email, String userName, int eventPlayed, int score, int correctAttempted, int wrongAttempted, int unAttempted, int userId) {
            this.facebookId = facebookId;
            this.gcmId = gcmId;
            this.email = email;
            this.userName = userName;
            this.eventPlayed = eventPlayed;
            this.score = score;
            this.correctAttempted = correctAttempted;
            this.wrongAttempted = wrongAttempted;
            this.unAttempted = unAttempted;
            this.userId = userId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getFacebookId() {
            return facebookId;
        }

        public void setFacebookId(String facebookId) {
            this.facebookId = facebookId;
        }

        public String getGcmId() {
            return gcmId;
        }

        public void setGcmId(String gcmId) {
            this.gcmId = gcmId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getEventPlayed() {
            return eventPlayed;
        }

        public void setEventPlayed(int eventPlayed) {
            this.eventPlayed = eventPlayed;
        }

        public int getCorrectAttempted() {
            return correctAttempted;
        }

        public void setCorrectAttempted(int correctAttempted) {
            this.correctAttempted = correctAttempted;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getWrongAttempted() {
            return wrongAttempted;
        }

        public void setWrongAttempted(int wrongAttempted) {
            this.wrongAttempted = wrongAttempted;
        }

        public int getUnAttempted() {
            return unAttempted;
        }

        public void setUnAttempted(int unAttempted) {
            this.unAttempted = unAttempted;
        }
    }

}
