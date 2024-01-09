package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.sql.Date;

public class Cli {

    Scanner scan;   // Scanner-Objekt für die Eingabe von der Konsole.

    MyCourseRepository repo;    // Repository-Objekt für den Zugriff auf Kursdaten.

    /**
     * Konstruktor für Cli.
     * Initialisiert den Scanner und setzt das Repository-Objekt.
     * @param repo Das Kurs-Repository-Objekt, das für Datenbankoperationen verwendet wird.
     */
    public Cli(MyCourseRepository repo)
    {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }
    /**
     * Startet das Command Line Interface.
     * Diese Methode zeigt das Hauptmenü an und verarbeitet Benutzereingaben.
     */
    public void start()
    {
        String input = "-";
        while(!input.equals("x"))
        {
            showMenue();
            input = scan.nextLine();
            switch(input)
            {
                case "1":
                    addCourse();
                    break;

                case "2":
                    showAllCourses();
                    break;

                case "3":
                    showCourseDetails();
                    break;

                case "4":
                    updateCourseDetails();
                    break;

                case "5":
                    deleteCourse();
                    break;

                case "6":
                    courseSearch();
                    break;

                case "7":
                    runningCourses();
                    break;

                case "x":
                    System.out.println("Auf Wiedersehen");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    /**
     * Zeigt eine Liste der aktuell laufenden Kurse an.
     * Ruft das Kurs-Repository auf, um alle laufenden Kurse zu finden und zeigt sie an.
     */
    private void runningCourses()
    {
        System.out.println("Aktuell laufende Kurse: ");

        // Erstellt eine leere Liste für Kurse.
        List<Course> list = new ArrayList<>();
        try
        {
            // Ruft die Methode findAllRunningCourses des Repository auf, um alle laufenden Kurse zu erhalten.
            list = repo.findAllRunningCourses();

            // Durchläuft die Liste der Kurse und gibt jeden Kurs auf der Konsole aus.
            for(Course course : list)
            {
                // Hier wird die toString()-Methode von Course aufgerufen.
                System.out.println(course);
            }
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler bei Kurs-Anzeige für laufende Kurse: " + databaseException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler bei der Kurs-Anzeige für laufende Kurse: " + exception.getMessage());
        }
    }

    /**
     * Ermöglicht dem Benutzer, nach Kursen zu suchen, indem er einen Suchbegriff eingibt.
     * Die Suche kann sich auf den Namen oder die Beschreibung des Kurses beziehen.
     */
    private void courseSearch()
    {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try
        {
            // Verwendet das Repository, um Kurse zu finden, deren Namen oder Beschreibung den Suchbegriff enthalten.
            courseList = repo.findAllCoursesByNameOrDescription(searchString);

            // Iteriert durch die Liste der gefundenen Kurse und gibt jeden auf der Konsole aus.
            for(Course course : courseList)
            {
                System.out.println(course);
            }
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler bei der Kurssuche: " +databaseException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler bei der Suche" +exception.getMessage());
        }
    }

    /**
     * Ermöglicht dem Benutzer, einen Kurs durch Eingabe der Kurs-ID zu löschen.
     */
    private void deleteCourse() {
        System.out.println("Welchen Kurs möchten Sie löschen bitte ID eingeben!");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try
        {
            boolean isDeleted = repo.deleteById(courseIdToDelete);

            //repo.deleteById(courseIdToDelete);
            //System.out.println("Datensatz mit ID: " + courseIdToDelete +  " erfolgreich gelöscht!");

            if(isDeleted)
            {
                System.out.println("Datensatz mit ID: " + courseIdToDelete + " erfolgreich gelöscht!");
            }
            else
            {
                System.out.println("Kein Datensatz mit ID: "+ courseIdToDelete + " gefunden!");
            }
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("DAtenbankfehler beim Löschen: " + databaseException.getMessage());
        }
        catch(Exception e)
        {
            System.out.println("Unbekannter Fehler beim Löschen: " +e.getMessage());
        }
    }

    /**
     * Ermöglicht dem Benutzer, die Details eines bestehenden Kurses zu aktualisieren.
     * Der Benutzer gibt die Kurs-ID ein und kann dann verschiedene Attribute des Kurses ändern.
     */
    private void updateCourseDetails()
    {
        System.out.println("Für welche Kurs-ID möchten Sie die Kursdetails ändern?");

        Long courseId = Long.parseLong(scan.nextLine());

        try
        {
            // Versucht, den Kurs mit der angegebenen ID zu finden.
            Optional<Course> courseOptional = repo.getById(courseId);
            if(courseOptional.isEmpty())
            {
                // Informiert den Benutzer, wenn kein Kurs mit der angegebenen ID gefunden wird.
                System.out.println("Kurs mit der angegebenen ID nicht in der Datenbank!");
            }
            else
            {
                Course course = courseOptional.get();

                System.out.println("Änderung für folgenden Kurs: ");
                System.out.println(course);

                // Aufforderung an den Benutzer, neue Werte für die Kursattribute einzugeben.
                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Änderung gewünscht ist!");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschriebung ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD): ");
                dateFrom = scan.nextLine();
                System.out.println("Enddatum (YYYY-MM-DD): ");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (ZA/BF/FF/OE)");
                courseType = scan.nextLine();

                // Aktualisiert den Kurs mit den neuen Werten oder behält die alten Werte bei, wenn keine Eingabe erfolgt.
                Optional<Course> optionalCourseUpdated = repo.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours.equals("") ? course.getHours():Integer.parseInt(hours),
                                dateFrom.equals("") ? course.getBeginDate():Date.valueOf(dateFrom),
                                dateTo.equals("")?course.getEndDate():Date.valueOf(dateTo),
                                courseType.equals("")?course.getCourseType():CourseType.valueOf(courseType)
                        )
                );

                // Gibt Feedback, ob der Kurs erfolgreich aktualisiert wurde.
                // (dass wirs mal gesehen haben in funktioneller programmierung)
                optionalCourseUpdated.ifPresentOrElse(
                        (c)-> System.out.println("Kurs aktualisiert: " + c),
                        ()-> System.out.println("Kurs konnte nicht aktualisiert werden!")
                );

            }
        }
        // Behandelt verschiedene Arten von Ausnahmen, die beim Aktualisieren auftreten können.
        catch(IllegalArgumentException illegalArgumentException)
        {
            System.out.println("Einagbefehler" +illegalArgumentException.getMessage());
        }
        catch(InvalidValueException invalidValueException)
        {
            System.out.println("Kursdaten nicht korrekt angegeben: " +invalidValueException.getMessage());
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    /**
     * Ermöglicht dem Benutzer, einen neuen Kurs hinzuzufügen.
     * Der Benutzer gibt alle notwendigen Kursdaten ein, um einen neuen Kurs zu erstellen.
     */
    private void addCourse()
    {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;

        try
        {
            System.out.println("Bitte alle Kursdaten angeben: ");
            System.out.println("Name: ");
            name = scan.nextLine();
            if(name.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if(description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Stundenanzahl");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp (ZA/BF/FF/OE): ");
            courseType = CourseType.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo.insert(
                    new Course(name,description,hours,dateFrom,dateTo,courseType)
            );

            if(optionalCourse.isPresent())
            {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            }
            else
            {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }
        }
        catch(IllegalArgumentException illegalArgumentException)
        {
            System.out.println("Einagbefehler" +illegalArgumentException.getMessage());
        }
        catch(InvalidValueException invalidValueException)
        {
            System.out.println("Kursdaten nicht korrekt angegeben: " +invalidValueException.getMessage());
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    /**
     * Ermöglicht dem Benutzer, Details zu einem bestimmten Kurs anzuzeigen.
     * Der Benutzer gibt die ID des Kurses ein, dessen Details angezeigt werden sollen.
     */
    private void showCourseDetails() {
        System.out.println("Bitte geben Sie die ID ein um die jeweiligen Kursdetails anzeigen zu lassen: ");
        Long courseId = Long.parseLong(scan.nextLine());

        try
        {
            // Versucht, den Kurs mit der angegebenen ID zu finden.
            Optional<Course> courseOptional = repo.getById(courseId);

            // Überprüft, ob der Kurs gefunden wurde und zeigt die Details an.
            if(courseOptional.isPresent())
            {
                System.out.println(courseOptional.get());
            }
            else
            {
                // Informiert den Benutzer, wenn kein Kurs mit der angegebenen ID gefunden wird.
                System.out.println("Kurs mit der ID " + courseId + " nicht gefunden!");
            }
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException);
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler bei Kurs-Detailanzeige: " + exception.getMessage());
        }
    }

    /**
     * Zeigt eine Liste aller Kurse an.
     * Diese Methode ruft das Kurs-Repository auf, um eine Liste aller vorhandenen Kurse zu erhalten und zeigt sie an.
     */
    private void showAllCourses()
    {
        List<Course> list = null;

        try
        {
            // Ruft die Methode getAll des Repository auf, um eine Liste aller Kurse zu erhalten.
            list = repo.getAll();
            //System.out.println("SSSS" + list.size());

            // Überprüft, ob die Kursliste Einträge enthält.
            if(list.size()>0)
            {
                // Durchläuft die Liste der Kurse und gibt jeden Kurs auf der Konsole aus.
                for(Course course : list)
                {
                    // Hier wird die toString()-Methode von Course aufgerufen.
                    System.out.println(course);
                }
            }
            else
            {
                // Informiert den Benutzer, wenn die Kursliste leer ist.
                System.out.println("Kursliste leer!");
            }
        }
        catch(DatabaseException databaseException)
        {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        }
        catch(Exception exception)
        {
            System.out.println("Unbekannter Fehler bei der Anzeige aller Kurse: " + exception.getMessage());
        }
    }

    private void showMenue()
    {
        System.out.println("------------- KURSMANAGEMENT -------------");
        System.out.println("(1) Kurs eingeben \t \t \t CREATE");
        System.out.println("(2) alle Kurse anzeigen \t READ");
        System.out.println("(3) Kursdetails anzeigen \t READ");
        System.out.println("(4) Kursdetails ändern \t \t UPDATE");
        System.out.println("(5) Kursdetails löschen \t DELETE");
        System.out.println("(6) Kurssuche \t \t \t \t READ");
        System.out.println("(7) Laufende Kurse \t \t \t READ");
        System.out.println("------------------------------------------");
        System.out.println("--------------   (x) ENDE   --------------");
    }

    private void inputError()
    {
        System.out.println("ungültige Eingabe! try it again!");
    }
}
