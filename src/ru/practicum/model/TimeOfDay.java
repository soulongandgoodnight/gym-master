package ru.practicum.model;

import java.util.Objects;

public class TimeOfDay implements Comparable<TimeOfDay> {

    //часы (от 0 до 23)
    private final int hours;
    //минуты (от 0 до 59)
    private final int minutes;

    public TimeOfDay(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public int compareTo(TimeOfDay other) {
        var thisTotalMinutes = this.getHours() * 60 + this.getMinutes();
        var otherTotalMinutes = other.getHours() * 60 + other.getMinutes();
        return thisTotalMinutes - otherTotalMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeOfDay timeOfDay)) return false;
        return getHours() == timeOfDay.getHours() && getMinutes() == timeOfDay.getMinutes();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHours(), getMinutes());
    }
}
