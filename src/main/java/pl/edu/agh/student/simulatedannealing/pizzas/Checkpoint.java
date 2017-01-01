package pl.edu.agh.student.simulatedannealing.pizzas;

import static java.lang.Math.abs;

class Checkpoint {
    private final int x;
    private final int y;

    Checkpoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int dist(Checkpoint other) {
        assert null != other;
        return abs(this.x - other.x) + abs(this.y - other.y);
    }

    @Override
    public String toString() {
        return "( " + x + ", " + y + " )";
    }
}
