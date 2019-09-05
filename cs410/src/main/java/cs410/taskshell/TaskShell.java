package cs410.taskshell;

import com.budhash.cliche.Command;
import com.budhash.cliche.ShellFactory;

import java.io.IOException;
import java.sql.*;

public class TaskShell {
    private final Connection db;

    public TaskShell(Connection cxn) {
        db = cxn;
    }

    @Command
    public void active() throws SQLException {
        String query = "SELECT task_id, task_name, task_created, task_due FROM task WHERE task_completed = FALSE "
                + "AND task_cancelled = FALSE ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            System.out.format("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Created", "Due");
            while (rs.next()) {
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                String created = rs.getString("task_created");
                String due = rs.getString("task_due");
                if (due == null) {
                    due = "";
                }
                ;
                System.out.format("%-5d %-30s %-20s %-20s%n", id, name, created, due);
            }
        }
    }

    @Command
    public void active(String tag) throws SQLException {
        String query = "SELECT task_id, task_name, task_created, task_due FROM task JOIN tag_ref USING (task_id) "
                + "JOIN tag USING (tag_id) WHERE task_completed = FALSE AND task_cancelled = FALSE "
                + "AND tag_name LIKE ? ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, tag);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.format("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Created", "Due");
                while (rs.next()) {
                    int id = rs.getInt("task_id");
                    String name = rs.getString("task_name");
                    String created = rs.getString("task_created");
                    String due = rs.getString("task_due");
                    if (due == null) {
                        due = "";
                    }
                    ;
                    System.out.format("%-5d %-30s %-20s %-20s%n", id, name, created, due);
                }
            }
        }
    }

    @Command
    public void completed(String tag) throws SQLException {
        String query = "SELECT task_id, task_name, task_created, task_due FROM task JOIN tag_ref USING (task_id) "
                + "JOIN tag USING (tag_id) WHERE task_completed = True AND task_cancelled = False "
                + "AND tag_name LIKE ? ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, tag);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.format("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Created", "Due");
                while (rs.next()) {
                    int id = rs.getInt("task_id");
                    String name = rs.getString("task_name");
                    String created = rs.getString("task_created");
                    String due = rs.getString("task_due");
                    if (due == null) {
                        due = "";
                    }
                    ;
                    System.out.format("%-5d %-30s %-20s %-20s%n", id, name, created, due);
                }
            }
        }
    }

    @Command
    public void add(String... name) throws SQLException {
        String insert = "INSERT INTO task (task_name) VALUES (?)";
        String fullname = name[0];
        for (int i = 1; i < name.length; i++) {
            fullname = fullname + " " + name[i];
        }
        try (PreparedStatement stmt = db.prepareStatement(insert);) {
            stmt.setString(1, fullname);
            stmt.executeUpdate();
        }
    }

    @Command
    public void due(int index, String date) throws SQLException {
        String update = "UPDATE task SET task_due = ? WHERE task_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setString(1, date);
            stmt.setInt(2, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void due(String range) throws SQLException {
        String query;
        if (range.toLowerCase().equals("today")) {
            query = "SELECT task_id, task_name, task_created, task_due FROM task WHERE task_completed = FALSE "
                    + "AND task_cancelled = FALSE AND task_due = CURDATE() ORDER BY task_id";

        } else if (range.toLowerCase().equals("soon")) {
            query = "SELECT task_id, task_name, task_created, task_due FROM task WHERE task_completed = FALSE "
                    + "AND task_cancelled = FALSE AND task_due >= CURDATE() AND task_due < CURDATE() + 3 "
                    + "ORDER BY task_id";
        } else {
            System.out.println("Valid options are soon and today.");
            return;
        }
        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            System.out.format("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Created", "Due");
            while (rs.next()) {
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                String created = rs.getString("task_created");
                String due = rs.getString("task_due");
                if (due == null) {
                    due = "";
                }
                ;
                System.out.format("%-5d %-30s %-20s %-20s%n", id, name, created, due);
            }
        }
    }

    @Command
    public void overdue() throws SQLException {
        String query = "SELECT task_id, task_name, task_created, task_due FROM task WHERE task_completed = FALSE "
                + "AND task_cancelled = FALSE AND task_due < CURDATE() ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            System.out.format("%-5s %-30s %-20s %-20s%n", "ID", "Name", "Created", "Due");
            while (rs.next()) {
                int id = rs.getInt("task_id");
                String name = rs.getString("task_name");
                String created = rs.getString("task_created");
                String due = rs.getString("task_due");
                if (due == null) {
                    due = "";
                }
                ;
                System.out.format("%-5d %-30s %-20s %-20s%n", id, name, created, due);
            }
        }
    }

    @Command
    public void rename(int index, String... name) throws SQLException {
        String update = "UPDATE task SET task_name = ? WHERE task_id = ?";
        String fullname = name[0];
        for (int i = 1; i < name.length; i++) {
            fullname = fullname + " " + name[i];
        }
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setString(1, fullname);
            stmt.setInt(2, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void tag(int index, String... tags) throws SQLException {
        String select = "SELECT tag_id FROM tag WHERE tag_name = ?";
        String insert = "INSERT INTO tag (tag_name) VALUES (?)";
        String insert_ref = "INSERT IGNORE INTO tag_ref (tag_id, task_id) VALUES (?, ?)";
        db.setAutoCommit(false);
        try {
            for (int i = 0; i < tags.length; i++) {
                int id;
                try (PreparedStatement stmt = db.prepareStatement(select);) {
                    stmt.setString(1, tags[i]);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            id = rs.getInt("tag_id");
                        } else {
                            try (PreparedStatement stmt2 = db.prepareStatement(insert);) {
                                stmt2.setString(1, tags[i]);
                                stmt2.executeUpdate();
                                try (ResultSet rs2 = stmt.executeQuery()) {
                                    id = rs2.getInt("tag_id");
                                }
                            }
                        }
                    }
                }
                try (PreparedStatement stmt = db.prepareStatement(insert_ref);) {
                    stmt.setInt(1, id);
                    stmt.setInt(2, index);
                    stmt.executeUpdate();
                }

            }
            db.commit();
        } catch (SQLException | RuntimeException e) {
            db.rollback();
            throw e;
        } finally {
            db.setAutoCommit(true);
        }
    }

    @Command
    public void finish(int index) throws SQLException {
        String update = "UPDATE task SET task_completed = True WHERE task_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setInt(1, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void cancel(int index) throws SQLException {
        String update = "UPDATE task SET task_cancelled = True WHERE task_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setInt(1, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void unfinish(int index) throws SQLException {
        String update = "UPDATE task SET task_completed = False WHERE task_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setInt(1, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void uncancel(int index) throws SQLException {
        String update = "UPDATE task SET task_cancelled = False WHERE task_id = ?";
        try (PreparedStatement stmt = db.prepareStatement(update);) {
            stmt.setInt(1, index);
            stmt.executeUpdate();
        }
    }

    @Command
    public void show(int index) throws SQLException {
        String query = "SELECT task_id, task_name, task_created, task_due, task_completed, task_cancelled FROM task WHERE task_id = ? "
                + "ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setInt(1, index);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.format("%-5s %-30s %-20s %-20s %-10s%n", "ID", "Name", "Created", "Due", "Status");
                while (rs.next()) {
                    int id = rs.getInt("task_id");
                    String name = rs.getString("task_name");
                    String created = rs.getString("task_created");
                    String due = rs.getString("task_due");
                    if (due == null) {
                        due = "";
                    }
                    String status = "Active";
                    if (rs.getBoolean("task_completed")) {
                        status = "Completed";
                    } else if (rs.getBoolean("task_cancelled")) {
                        status = "Cancelled";
                    }
                    System.out.format("%-5d %-30s %-20s %-20s %-10s%n", id, name, created, due, status);

                    String query2 = "SELECT tag_name FROM tag JOIN tag_ref USING (tag_id) WHERE task_id = ?";
                    try (PreparedStatement stmt2 = db.prepareStatement(query2)) {
                        stmt2.setInt(1, index);
                        try (ResultSet rs2 = stmt2.executeQuery()) {
                            System.out.println("Tags:");
                            while (rs2.next()) {
                                String tag = rs2.getString("tag_name");
                                System.out.println(tag);
                            }
                        }
                    }
                }
            }
        }
    }

    @Command
    public void search(String... terms) throws SQLException {
        String fullsearch = terms[0];
        for (int i = 1; i < terms.length; i++) {
            fullsearch = fullsearch + " " + terms[i];
        }
        String query = "SELECT task_id, task_name, task_created, task_due, task_completed, task_cancelled FROM task WHERE MATCH (task_name) AGAINST (?) "
                + "ORDER BY task_id";
        try (PreparedStatement stmt = db.prepareStatement(query)) {
            stmt.setString(1, fullsearch);
            try (ResultSet rs = stmt.executeQuery()) {
                System.out.format("%-5s %-30s %-20s %-20s %-10s%n", "ID", "Name", "Created", "Due", "Status");
                while (rs.next()) {
                    int id = rs.getInt("task_id");
                    String name = rs.getString("task_name");
                    String created = rs.getString("task_created");
                    String due = rs.getString("task_due");
                    if (due == null) {
                        due = "";
                    }
                    String status = "Active";
                    if (rs.getBoolean("task_completed")) {
                        status = "Completed";
                    } else if (rs.getBoolean("task_cancelled")) {
                        status = "Cancelled";
                    }
                    System.out.format("%-5d %-30s %-20s %-20s %-10s%n", id, name, created, due, status);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        if (args.length != 1) {
            System.out.println(
                    "Usage:\n\tjava -jar taskshell.jar mysql:server:port/database?user=username&password=passwd");
        } else {
            String dbUrl = args[0];
            try (Connection cxn = DriverManager.getConnection("jdbc:" + dbUrl)) {
                TaskShell shell = new TaskShell(cxn);
                ShellFactory.createConsoleShell("task", "", shell).commandLoop();
            }
        }
    }
}

/* vim: ts=4:sw=4:expandtab
*/
