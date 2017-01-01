package pl.edu.agh.student.simulatedannealing.pizzas;

class PizzaOrder extends Checkpoint {
    final int deadline;

    PizzaOrder(int x, int y, int deadline) {
        super(x, y);
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + deadline + ")";
    }
}
