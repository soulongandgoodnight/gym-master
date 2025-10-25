package ru.practicum.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        //given
        var timetable = new Timetable();

        var group = new Group("Акробатика для детей", Age.CHILD, 60);
        var coach = new Coach("Васильев", "Николай", "Сергеевич");
        var singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        // do
        var mondayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        var tuesdayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        //expect
        // Проверить, что за понедельник вернулось одно занятие
        var mondayTrainingSessionsCount = mondayTrainingSessions.values().stream().mapToInt(List::size).sum();
        assertEquals(1, mondayTrainingSessionsCount);

        // Проверить, что за вторник не вернулось занятий
        var tuesdayTrainingSessionsCount = tuesdayTrainingSessions.values().stream().mapToInt(List::size).sum();
        assertEquals(0, tuesdayTrainingSessionsCount);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        //given
        var timetable = new Timetable();

        var coach = new Coach("Васильев", "Николай", "Сергеевич");

        var groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        var thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        var groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        var mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        var thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        var saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // do
        var mondayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        var tuesdayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        var thursdayTrainingSessions = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        // expect
        // Проверить, что за понедельник вернулось одно занятие
        var mondayTrainingSessionsCount = mondayTrainingSessions.values().stream().mapToInt(List::size).sum();
        assertEquals(1, mondayTrainingSessionsCount);

        // Проверить, что за вторник не вернулось занятий
        var tuesdayTrainingSessionsCount = tuesdayTrainingSessions.values().stream().mapToInt(List::size).sum();
        assertEquals(0, tuesdayTrainingSessionsCount);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        var thursdaySessionsFlat = thursdayTrainingSessions.values().stream().flatMap(List::stream).toList();
        assertEquals(2, thursdaySessionsFlat.size());
        assertEquals(new TimeOfDay(13, 0), thursdaySessionsFlat.getFirst().getTimeOfDay());
        assertEquals(new TimeOfDay(20, 0), thursdaySessionsFlat.getLast().getTimeOfDay());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        //given
        var timetable = new Timetable();

        var group = new Group("Акробатика для детей", Age.CHILD, 60);
        var coach = new Coach("Васильев", "Николай", "Сергеевич");
        var thirteenHours = new TimeOfDay(13, 0);
        var fourteenHours = new TimeOfDay(14, 0);
        var singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, thirteenHours);

        timetable.addNewTrainingSession(singleTrainingSession);

        // do
        var mondayThirteenHoursSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, thirteenHours);
        var mondayFourteenHoursSessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, fourteenHours);

        // expect
        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        assertEquals(1, mondayThirteenHoursSessions.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        assertEquals(0, mondayFourteenHoursSessions.size());
    }

    @Test
    void addTrainingSessionForIncorrectDayDoesNothing(){
        //given
        var timetable = new Timetable();

        var group = new Group("Акробатика для детей", Age.CHILD, 60);
        var coach = new Coach("Васильев", "Николай", "Сергеевич");
        var twentyFourHours = new TimeOfDay(24, 0);
        var singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, twentyFourHours);

        // do
        timetable.addNewTrainingSession(singleTrainingSession);

        // expect
        // Проверить, что тренировка в 24:00 не добавилась в расписание
        var mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(0, mondaySessions.size());
    }

    @Test
    void addTrainingSessionForIncorrectTimeDoesNothing(){
        //given
        var timetable = new Timetable();

        var group = new Group("Акробатика для детей", Age.CHILD, 60);
        var coach = new Coach("Васильев", "Николай", "Сергеевич");
        var OneHourSixtyMinutes = new TimeOfDay(1, 60);
        var singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, OneHourSixtyMinutes);

        // do
        timetable.addNewTrainingSession(singleTrainingSession);

        // expect
        // Проверить, что тренировка в 01:60 не добавилась в расписание
        var mondaySessions = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertEquals(0, mondaySessions.size());
    }

    @Test
    void addTwoTrainingSessionsAtTheSameTime(){
        //given
        var timetable = new Timetable();

        var childGroup = new Group("Акробатика для детей", Age.CHILD, 60);
        var childCoach = new Coach("Васильев", "Николай", "Сергеевич");
        var adultGroup = new Group("Акробатика для взрослых", Age.ADULT, 60);
        var adultCoach = new Coach("Николаев", "Василий", "Сергеевич");
        var thirteenHours = new TimeOfDay(13, 0);

        var childTrainingSession = new TrainingSession(childGroup, childCoach,
                DayOfWeek.MONDAY, thirteenHours);
        var adultTrainingSession = new TrainingSession(adultGroup, adultCoach,
                DayOfWeek.MONDAY, thirteenHours);

        // do
        timetable.addNewTrainingSession(childTrainingSession);
        timetable.addNewTrainingSession(adultTrainingSession);
        var mondaySessions = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, thirteenHours);

        // expect
        // Проверить, что сохранились 2 тренировки
        assertEquals(2, mondaySessions.size());
    }

}
