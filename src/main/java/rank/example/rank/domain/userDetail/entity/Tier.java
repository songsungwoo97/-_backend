package rank.example.rank.domain.userDetail.entity;

public enum Tier {
    BRONZE(0),
    SILVER(1),
    GOLD(2),
    PLATINUM(3),
    DIAMOND(4);

    private final int rank;

    Tier(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
