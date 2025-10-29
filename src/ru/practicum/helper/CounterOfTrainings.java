package ru.practicum.helper;

import ru.practicum.model.Coach;

public record CounterOfTrainings(Coach coach, int trainingsCount) implements Comparable<CounterOfTrainings> {
    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.trainingsCount() - this.trainingsCount();
    }
}
