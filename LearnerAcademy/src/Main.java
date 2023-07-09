import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class Subject {
    private int id;
    private String name;

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Teacher {
    private int id;
    private String name;

    public Teacher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Class {
    private int id;
    private String name;

    public Class(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Student {
    private int id;
    private String name;
    private Class studentClass;

    public Student(int id, String name, Class studentClass) {
        this.id = id;
        this.name = name;
        this.studentClass = studentClass;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Class getStudentClass() {
        return studentClass;
    }
}

class ClassReport {
    private Class studentClass;
    private List<Subject> subjects;
    private List<Teacher> teachers;
    private List<Student> students;

    public ClassReport(Class studentClass, List<Subject> subjects, List<Teacher> teachers, List<Student> students) {
        this.studentClass = studentClass;
        this.subjects = subjects;
        this.teachers = teachers;
        this.students = students;
    }

    public Class getStudentClass() {
        return studentClass;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Student> getStudents() {
        return students;
    }
}

class LearnersAcademy {
    private Connection connection;

    public LearnersAcademy(Connection connection) {
        this.connection = connection;
    }

    public void createSubjectTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS subjects (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTeacherTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS teachers (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createClassTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS classes (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createStudentTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(100), class_id INT, FOREIGN KEY (class_id) REFERENCES classes(id))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createSubjectClassTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS subject_class (subject_id INT, class_id INT, FOREIGN KEY (subject_id) REFERENCES subjects(id), FOREIGN KEY (class_id) REFERENCES classes(id))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTeacherClassTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS teacher_class (teacher_id INT, class_id INT, FOREIGN KEY (teacher_id) REFERENCES teachers(id), FOREIGN KEY (class_id) REFERENCES classes(id))";
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertSubject(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (name) VALUES (?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTeacher(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (name) VALUES (?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertClass(String name) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO classes (name) VALUES (?)");
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertStudent(String name, int classId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, class_id) VALUES (?, ?)");
            statement.setString(1, name);
            statement.setInt(2, classId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignSubjectToClass(int subjectId, int classId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subject_class (subject_id, class_id) VALUES (?, ?)");
            statement.setInt(1, subjectId);
            statement.setInt(2, classId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void assignTeacherToClass(int teacherId, int classId) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teacher_class (teacher_id, class_id) VALUES (?, ?)");
            statement.setInt(1, teacherId);
            statement.setInt(2, classId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM subjects";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Subject subject = new Subject(id, name);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM teachers";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Teacher teacher = new Teacher(id, name);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM classes";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Class studentClass = new Class(id, name);
                classes.add(studentClass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT students.id, students.name, classes.id AS class_id, classes.name AS class_name FROM students INNER JOIN classes ON students.class_id = classes.id";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int classId = resultSet.getInt("class_id");
                String className = resultSet.getString("class_name");
                Class studentClass = new Class(classId, className);
                Student student = new Student(id, name, studentClass);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public ClassReport generateClassReport(Class studentClass) {
        List<Subject> subjects = getSubjectsByClass(studentClass);
        List<Teacher> teachers = getTeachersByClass(studentClass);
        List<Student> students = getStudentsByClass(studentClass);
        return new ClassReport(studentClass, subjects, teachers, students);
    }

    private List<Subject> getSubjectsByClass(Class studentClass) {
        List<Subject> subjects = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT subjects.id, subjects.name FROM subjects INNER JOIN subject_class ON subjects.id = subject_class.subject_id WHERE subject_class.class_id = ?");
            statement.setInt(1, studentClass.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Subject subject = new Subject(id, name);
                subjects.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjects;
    }

    private List<Teacher> getTeachersByClass(Class studentClass) {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT teachers.id, teachers.name FROM teachers INNER JOIN teacher_class ON teachers.id = teacher_class.teacher_id WHERE teacher_class.class_id = ?");
            statement.setInt(1, studentClass.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Teacher teacher = new Teacher(id, name);
                teachers.add(teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private List<Student> getStudentsByClass(Class studentClass) {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT students.id, students.name, classes.id AS class_id, classes.name AS class_name FROM students INNER JOIN classes ON students.class_id = classes.id WHERE students.class_id = ?");
            statement.setInt(1, studentClass.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int classId = resultSet.getInt("class_id");
                String className = resultSet.getString("class_name");
                Class classObj = new Class(classId, className);
                Student student = new Student(id, name, classObj);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/learners_academy";
        String username = "your_username";
        String password = "your_password";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            LearnersAcademy learnersAcademy = new LearnersAcademy(connection);

            learnersAcademy.createSubjectTable();
            learnersAcademy.createTeacherTable();
            learnersAcademy.createClassTable();
            learnersAcademy.createStudentTable();
            learnersAcademy.createSubjectClassTable();
            learnersAcademy.createTeacherClassTable();

            // Add subjects, teachers, classes, students, and assign them accordingly
            // ...

            // Generate a class report
            Class classObj = new Class(1, "Class A");
            ClassReport classReport = learnersAcademy.generateClassReport(classObj);

            // Display the class report
            // ...
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
