public enum BaseStar {
    ONE_STAR,
    TWO_STAR,
    THREE_STAR;

    @Override
    public String toString() {
        switch (this) {
            case ONE_STAR: return "1 Star";
            case TWO_STAR: return "2 Star";
            case THREE_STAR: return "3 Star";
            default: return "";
        }
    }
}
