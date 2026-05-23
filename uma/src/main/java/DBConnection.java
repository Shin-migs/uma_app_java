import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static Map<String, String> loadEnv() {
        Map<String, String> env = new HashMap<>();
        File envFile = new File(".env");
        System.out.println("Looking for .env at: " + envFile.getAbsolutePath());
        System.out.println("File exists: " + envFile.exists());

        try (BufferedReader br = new BufferedReader(new FileReader(envFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    env.put(parts[0].trim(), parts[1].trim());
                }
            }
            System.out.println("✅ .env loaded successfully");
        } catch (Exception e) {
            System.out.println("❌ Could not load .env file: " + e.getMessage());
        }
        return env;
    }

    public static Connection connect() {
        Map<String, String> env = loadEnv();
        String url  = env.getOrDefault("DB_URL",  "");
        String user = env.getOrDefault("DB_USER", "");
        String pass = env.getOrDefault("DB_PASS", "");

        System.out.println("Connecting with URL: " + url);
        System.out.println("User: " + user);

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connected to Supabase successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("❌ Connection failed: " + e.getMessage());
            return null;
        }
    }
}
