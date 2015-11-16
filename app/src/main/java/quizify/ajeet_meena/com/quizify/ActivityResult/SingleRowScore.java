package quizify.ajeet_meena.com.quizify.ActivityResult;

public class SingleRowScore
{
    int rank;
    int score;
    String user_id;
    String user_name;

    public SingleRowScore(int score, int rank, String user_id, String user_name) {
        this.score = score;
        this.rank = rank;
        this.user_id = user_id;
        this.user_name = user_name;
    }
}
