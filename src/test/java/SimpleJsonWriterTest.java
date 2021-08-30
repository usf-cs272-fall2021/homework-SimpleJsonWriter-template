import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.TagFilter;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

/**
 * Tests the {@link SimpleJsonWriter} class.
 *
 * @author CS 272 Software Development (University of San Francisco)
 * @version Fall 2021
 */
@TestMethodOrder(MethodName.class)
public class SimpleJsonWriterTest {
	/**
	 * Tests the {@link SimpleJsonWriter#asArray(Collection, Path)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class A_ArrayTests {
		/**
		 * Tests the output of an empty set.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(1)
		@Test
		public void testEmptySet() throws IOException {
			String name = "json-array-empty.json";
			runTest(new LinkedList<Integer>(), name);
		}

		/**
		 * Tests the output of a set with a single element.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(2)
		@Test
		public void testSingleSet() throws IOException {
			String name = "json-array-single.json";

			HashSet<Integer> elements = new HashSet<>();
			elements.add(42);

			runTest(elements, name);
		}

		/**
		 * Tests the output of a simple set with several elements.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(3)
		@Test
		public void testSimpleSet() throws IOException {
			String name = "json-array-simple.json";

			TreeSet<Integer> elements = new TreeSet<>();
			Collections.addAll(elements, 65, 66, 67, 68);

			runTest(elements, name);
		}

		/**
		 * Tests the output of a simple list with several elements.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(4)
		@Test
		public void testSimpleList() throws IOException {
			String name = "json-array-simple.json";

			List<Integer> elements = new ArrayList<>();
			Collections.addAll(elements, 65, 66, 67, 68);

			runTest(elements, name);
		}

		/**
		 * Helper method for running tests in this nested class.
		 *
		 * @param elements the elements to write to JSON file
		 * @param name the name of the expected output file
		 *
		 * @throws IOException if an I/O error occurs
		 */
		public void runTest(Collection<Integer> elements, String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			SimpleJsonWriter.asArray(elements, actualPath);
			compareFiles(actualPath, expectPath);
		}
	}

	/**
	 * Tests the {@link SimpleJsonWriter#asObject(java.util.Map, Path)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class B_ObjectTests {
		/**
		 * Tests an empty map.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(1)
		@Test
		public void testEmptyMap() throws IOException {
			String name = "json-object-empty.json";
			runTest(new TreeMap<String, Integer>(), name);
		}

		/**
		 * Tests a map with one key/value pair.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(2)
		@Test
		public void testSinglePair() throws IOException {
			String name = "json-object-single.json";

			HashMap<String, Integer> elements = new HashMap<>();
			elements.put("The Answer", 42);

			runTest(elements, name);
		}

		/**
		 * Tests a map with several key/value pairs.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(3)
		@Test
		public void testSimpleMap() throws IOException {
			String name = "json-object-simple.json";

			TreeMap<String, Integer> elements = new TreeMap<>();
			elements.put("a", 4);
			elements.put("b", 3);
			elements.put("c", 2);
			elements.put("d", 1);

			runTest(elements, name);
		}

		/**
		 * Helper method for running tests in this nested class.
		 *
		 * @param elements the elements to write to JSON file
		 * @param name the name of the expected output file
		 *
		 * @throws IOException if an I/O error occurs
		 */
		public void runTest(Map<String, Integer> elements, String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			SimpleJsonWriter.asObject(elements, actualPath);
			compareFiles(actualPath, expectPath);
		}
	}

	/**
	 * Tests the {@link SimpleJsonWriter#asNestedArray(java.util.Map, Path)} method.
	 */
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class C_NestedObjectTests {
		/**
		 * Tests an empty nested map.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(1)
		@Test
		public void testEmptyMap() throws IOException {
			String name = "json-object-empty.json";
			runTest(new TreeMap<String, ArrayList<Integer>>(), name);
		}

		/**
		 * Tests a nested map with a single entry.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(2)
		@Test
		public void testSingleEntry() throws IOException {
			String name = "json-nested-single.json";

			HashMap<String, HashSet<Integer>> elements = new HashMap<>();
			elements.put("The Answer", new HashSet<>());
			elements.get("The Answer").add(42);

			runTest(elements, name);
		}

		/**
		 * Tests a nested map with several entries.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@Order(3)
		@Test
		public void testSimpleTree() throws IOException {
			String name = "json-nested-simple.json";

			TreeMap<String, TreeSet<Integer>> elements = new TreeMap<>();
			elements.put("a", new TreeSet<>());
			elements.put("b", new TreeSet<>());
			elements.put("c", new TreeSet<>());

			elements.get("a").add(1);
			elements.get("b").add(2);
			elements.get("b").add(3);
			elements.get("b").add(4);

			runTest(elements, name);
		}

		/**
		 * Helper method for running tests in this nested class.
		 *
		 * @param elements the elements to write to JSON file
		 * @param name the name of the expected output file
		 *
		 * @throws IOException if an I/O error occurs
		 */
		public void runTest(Map<String, ? extends Collection<Integer>> elements, String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			SimpleJsonWriter.asNestedArray(elements, actualPath);
			compareFiles(actualPath, expectPath);
		}
	}

	/**
	 * Imperfect tests to try and determine if the approach has any issues.
	 */
	@Tag("approach")
	@Nested
	@TestMethodOrder(OrderAnnotation.class)
	public class D_ApproachTests {
		/**
		 * Tests that various methods do NOT appear in the source code.
		 *
		 * @param method the unauthorized method
		 */
		@ParameterizedTest(name = "[{index}: \"{0}\"]")
		@ValueSource(strings = {
				"replace", "replaceAll", "replaceFirst", "split", "join"
		})
		public void testInvalidMethods(String method) {
			String regex = "\\." + method + "\\(";
			long count = Pattern.compile(regex).matcher(source).results().count();
			Assertions.assertTrue(count < 1, "Found " + count + " calls of " + method + " in source code.");
		}

		/**
		 * Causes this group of tests to fail if the other non-approach tests are not
		 * yet passing.
		 */
		@Test
		public void testOthersPassing() {
			var request = LauncherDiscoveryRequestBuilder.request()
					.selectors(DiscoverySelectors.selectClass(SimpleJsonWriterTest.class))
					.filters(TagFilter.excludeTags("approach"))
					.build();

			var launcher = LauncherFactory.create();
			var listener = new SummaryGeneratingListener();

			Logger logger = Logger.getLogger("org.junit.platform.launcher");
			logger.setLevel(Level.SEVERE);

			launcher.registerTestExecutionListeners(listener);
			launcher.execute(request);

			Assertions.assertEquals(0, listener.getSummary().getTotalFailureCount(),
					"Must pass other tests to earn credit for approach group!");
		}

		/** Source code loaded as a String object. */
		private String source;

		/**
		 * Sets up the parser object with a single test case.
		 *
		 * @throws IOException if an I/O error occurs
		 */
		@BeforeEach
		public void setup() throws IOException {
			String name = SimpleJsonWriter.class.getSimpleName() + ".java";
			Path path = Path.of("src", "main", "java", name);
			Assertions.assertTrue(Files.isReadable(path), "Unable to access source code.");
			this.source = Files.readString(path, StandardCharsets.UTF_8);
		}
	}

	/** Location of actual output produced by running tests. */
	public static final Path ACTUAL_DIR = Path.of("out");

	/** Location of expected output. */
	public static final Path EXPECTED_DIR = Path.of("src", "test", "resources");

	/**
	 * Makes sure the actual output directory exists.
	 *
	 * @throws IOException if an I/O error occurs
	 */
	@BeforeAll
	public static void setupEnvironment() throws IOException {
		Files.createDirectories(ACTUAL_DIR);
	}

	/**
	 * Compares two files as String objects. Strips any trailing whitespace
	 * (including newlines) before comparing.
	 *
	 * @param actualPath the path to the first file (the actual output)
	 * @param expectPath the path to the second file (the expected output)
	 *
	 * @throws IOException if an I/O error occurs
	 */
	public static void compareFiles(Path actualPath, Path expectPath) throws IOException {
		String actual = Files.readString(actualPath, StandardCharsets.UTF_8).stripTrailing();
		String expect = Files.readString(expectPath, StandardCharsets.UTF_8).stripTrailing();

		String format = """

				Compare %s and %s for differences.
				""";

		String debug = String.format(format, actualPath, expectPath);
		Assertions.assertEquals(expect, actual, () -> debug);
	}
}
