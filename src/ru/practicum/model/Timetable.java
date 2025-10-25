package ru.practicum.model;

import java.util.*;

public class Timetable {

    private final Hashtable<DayOfWeek, SortedMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public Timetable(){
        var daysOfWeek = DayOfWeek.values();
        timetable = new Hashtable<>(daysOfWeek.length);
        for (var dayOfWeek: daysOfWeek) {
            timetable.put(dayOfWeek, new TreeMap<>());
        }
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        var dayOfWeek = trainingSession.getDayOfWeek();
        var timeOfDay = trainingSession.getTimeOfDay();
        if (!TimeIsValid(timeOfDay)) {
            return;
        }

        if (!timetable.containsKey(dayOfWeek)) {
            return;
        }

        var sessionsOfDay = timetable.get(dayOfWeek);
        var sessionsOfDayAndTime = sessionsOfDay.getOrDefault(timeOfDay, null);
        if (sessionsOfDayAndTime == null) {
            sessionsOfDayAndTime = new ArrayList<>();
            sessionsOfDay.put(timeOfDay, sessionsOfDayAndTime);
        }

        sessionsOfDayAndTime.add(trainingSession);
    }

    private boolean TimeIsValid(TimeOfDay timeOfDay) {
        var hours = timeOfDay.getHours();
        var minutes = timeOfDay.getMinutes();
        return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
    }

    public SortedMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        var trainingSessionsByDay = timetable.get(dayOfWeek);
        return trainingSessionsByDay.getOrDefault(timeOfDay, new ArrayList<>());
    }
}
