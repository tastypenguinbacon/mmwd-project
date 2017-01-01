package pl.edu.agh.student.simulatedannealing.pizzas;

import java.util.*;

import static java.lang.Math.min;

class Route {
    private List<Checkpoint> list;

    private static Random rand = new Random();
    static void setRand(Random generator) {
        rand = generator;
    }

    Route(List<Checkpoint> route) {
        this.list = route;
    }

    Route(Route other) {
        // shallow copy
        this.list = new LinkedList<>(other.getList());
    }

    List<Checkpoint> getList() {
        return list;
    }

    boolean isValid() {
        int time = 0;
        Iterator<Checkpoint> it = list.iterator();
        if (!it.hasNext()) {
            return true;
        }

        Checkpoint previous = it.next();
        while (it.hasNext()) {
            final Checkpoint current = it.next();
            time += current.dist(previous);
            if ((current instanceof PizzaOrder) && (time > ((PizzaOrder) current).deadline)) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    private int routeLength() {
        int time = 0;
        Iterator<Checkpoint> it = list.iterator();
        Checkpoint previous = it.hasNext() ? it.next() : null;
        while (it.hasNext()) {
            final Checkpoint current = it.next();
            time += current.dist(previous);
            previous = current;
        }
        return time;
    }

    private List<Integer> determineValidInsertionPositions(PizzaOrder order){
        List<Integer> positions = new LinkedList<>();
        int currentTime = routeLength();
// Iterate in reverse.
        ListIterator<Checkpoint> li = list.listIterator(list.size());
        Checkpoint later = li.hasPrevious() ? li.previous() : null;
//        Checkpoint later = null;
        int timeOfArrival;

//        if (li.hasPrevious()) {
        if (null != later) {
//            later = li.previous();
            assert li.nextIndex() == list.size() - 1;
            timeOfArrival = currentTime + later.dist(order);
            if (timeOfArrival <= order.deadline) {
                positions.add(li.nextIndex()); // index of last element
            }
        }

        int timeMargin = Integer.MAX_VALUE;
        while (li.hasPrevious()) {
            final Checkpoint current = li.previous();
            int step = current.dist(later);
            currentTime -= step;

            //update timeMargin
            if (later instanceof PizzaOrder) {
                int localMargin = ((PizzaOrder) later).deadline - currentTime - step;
                timeMargin = min(timeMargin, localMargin);
            }

            //check for insertion validity
            timeOfArrival = currentTime + current.dist(order);
            if (timeOfArrival <= order.deadline) {
                int overhead = current.dist(order) + order.dist(later) - current.dist(later);
                if (overhead <= timeMargin)
                    positions.add(li.nextIndex()); // index of current
            }

            later = current;
        }
        assert 0 == currentTime : currentTime;

        return positions;
    }

    boolean insertOrderAtRandomValidPosition(PizzaOrder order) {

        //List<Checkpoint> validInsertionPositions = new LinkedList<>();
        List<Integer> validInsertionPositions = determineValidInsertionPositions(order);

        if (validInsertionPositions.isEmpty()) {
            return false;
        }

        int index = rand.nextInt(validInsertionPositions.size());
        int position = validInsertionPositions.get(index);
        try {
            this.list.add(position + 1, order);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("order: " + order);
            System.out.println("list.size(): " + list.size());
            System.out.println("position: " + position);
            System.out.println("list (the route):\n" + list);
            throw e;
        }

        return true;
    }

    @Override
    public String toString() {

        String str = "";
        int time = 0;
        Iterator<Checkpoint> it = list.iterator();
        if (!it.hasNext()) {
            return str;
        }

        Checkpoint previous = it.next();
        str += time + "\t: " + previous;
        while (it.hasNext()) {
            final Checkpoint current = it.next();
            time += current.dist(previous);
            str += "\n" + time + "\t: " + current;
            previous = current;
        }
        return str;
    }
}
