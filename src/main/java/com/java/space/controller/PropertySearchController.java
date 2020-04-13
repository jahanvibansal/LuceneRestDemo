package com.java.space.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.space.dto.Location;
import com.java.space.dto.Pricing;
import com.java.space.dto.Property;
import com.java.space.dto.Property.Category;
import com.java.space.dto.Property.Purpose;
import com.java.space.dto.Property.Timings;
import com.java.space.dto.Property.Type;
import com.java.space.dto.PropertyDetails;
import com.java.space.dto.PropertyDetails.Age;
import com.java.space.dto.PropertyDetails.AreaType;
import com.java.space.dto.PropertyDetails.AreaUnit;
import com.java.space.dto.PropertyDetails.Availability;
import com.java.space.dto.util.TypeConverter;
import com.java.space.service.PropertySearchService;

@RestController
@RequestMapping(path = "/property", produces = MediaType.APPLICATION_JSON_VALUE)
public class PropertySearchController {

	@Autowired
	PropertySearchService service;
	private Directory index = new RAMDirectory();
	{
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try (IndexWriter writer = new IndexWriter(index, config)) {

			Property p1 = Property.builder().availableFrom(LocalDate.now()).id(UUID.randomUUID().toString())
					.title("Office space for lease in Delhi road, Meerut")
					.description(
							"Two office space/ godown available, size 950 sq ft and 2500 sq ft respectively. Shaded. The bigger godown has one washroom also. 24 hours water supply. 2 mins walk from the main road. All facilities nearby.")
					.details(PropertyDetails.builder().availabiltiy(Availability.READY_TO_MOVE).maxArea(2500)
							.minArea(250).noOfWashrooms(1).propertyAge(Age.TEN_PLUS).type(AreaType.BUILTUP)
							.unit(AreaUnit.ACRES).build())
					.location(Location.builder().city("meerut").houseNumber("10r").locality("delhi road")
							.pincode(250002).build())
					.pricing(Pricing.builder().bookingAmount(25000).rentPerSqFt(150).build()).type(Type.COMMERCIAL)
					.category(Category.NORMAL).timing(Timings.DAILY).popularity(25).purpose(Purpose.RENT).build();
			addDocument(writer, p1);
			Property p2 = Property.builder().availableFrom(LocalDate.now()).title("3bhk for rent in Noida")
					.id(UUID.randomUUID().toString()).description("3 bhk fully furnished, in noida")
					.details(PropertyDetails.builder().availabiltiy(Availability.UNDER_CONSTRUCTION).maxArea(3000)
							.noOfWashrooms(3).propertyAge(Age.ZERO_TO_ONE).type(AreaType.CARPET)
							.unit(AreaUnit.SQUARE_FEET).build())
					.location(Location.builder().city("noida").houseNumber("507").locality("jaypee aman")
							.pincode(201301).build())
					.pricing(Pricing.builder().bookingAmount(30000).rentPerSqFt(100).build()).type(Type.ROOM)
					.timing(Timings.MONTHLY).category(Category.PREMIUM).popularity(20).purpose(Purpose.RENT).build();
			addDocument(writer, p2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@PostMapping(produces = MediaType.TEXT_HTML_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> storeDocument(@RequestBody @ModelAttribute Property property) {

		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		try (IndexWriter writer = new IndexWriter(index, config);) {
			addDocument(writer, property);
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

//http://localhost:9000/property/searchByTypePurposeCity?type=commercial&purpose=RENT&city=meerut
	@GetMapping(path = "/searchByTypePurposeCity")
	public ResponseEntity<Object> searchByTypePurposeAndCity(@RequestParam Type type, @RequestParam Purpose purpose,
			@RequestParam String city) {
		try {
			List<Document> list = service.searchByTypePurposeAndCity(index, type, purpose, city);
			return ResponseEntity.ok(list);
			/*
			 * return ResponseEntity.ok(list.stream().map(x -> { try {
			 * System.out.println(x); return documentToObject(x); } catch (Exception e) {
			 * 
			 * e.printStackTrace(); return ResponseEntity.badRequest().build(); }
			 * }).collect(Collectors.toList()));
			 */
			/*
			 * ObjectMapper mapper = new ObjectMapper(); String result =
			 * mapper.writeValueAsString(list);
			 */
		} catch (IOException | ParseException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}

	}

	// Search properties by combination of type, purpose and city. Each can expect
	@GetMapping(path = "/searchByTiming")
	public List<Document> searchByTimings(Directory index, Timings timing) throws IOException, ParseException {
		return service.searchByTimings(index, timing);
	}

	@GetMapping(path = "/searchBySpaceNameOrTitle")
	public List<Document> searchBySpaceNameOrTitle(Directory index, String text) throws IOException, ParseException {
		return service.searchBySpaceNameOrTitle(index, text);
	}

	@GetMapping(path = "/searchByAvailability")
	public List<Document> searchByAvailability(Directory index, Availability availability)
			throws IOException, ParseException {
		return service.searchByAvailability(index, availability);
	}

	@GetMapping(path = "/searchByPopularity")
	public List<Document> searchByPopularity(Directory index, int popularity) throws IOException, ParseException {
		return service.searchByPopularity(index, popularity);
	}

	@GetMapping(path = "/searchByCategory")
	public List<Document> searchByCategory(Directory index, Category category) throws IOException, ParseException {
		return service.searchByCategory(index, category);
	}

	private static void addDocument(IndexWriter writer, Property p) throws IOException {
		Document d1 = new Document();
		d1.add(new StringField("Id", p.getId(), Store.YES));
		d1.add(new StringField("Class", p.getClass().getName(), Store.YES));
		d1.add(new TextField("Title", p.getTitle(), Field.Store.YES));// analyzed by default
		d1.add(new TextField("Description", p.getDescription(), Field.Store.YES));
		// stored field is only stored, cannot be used for searching, not indexed
		d1.add(new StringField("Type", p.getType().toString(), Store.YES));
		d1.add(new StringField("Purpose", p.getPurpose().toString(), Field.Store.YES));
		d1.add(new StringField("City", p.getLocation().getCity(), Field.Store.YES));
		d1.add(new StringField("Timing", p.getTiming().toString(), Field.Store.YES));
		d1.add(new StringField("Availabiltiy", p.getDetails().getAvailabiltiy().toString(), Field.Store.YES));
		d1.add(new NumericDocValuesField("Popularity", p.getPopularity()));
		d1.add(new StringField("Category", p.getCategory().toString(), Store.YES));
		writer.addDocument(d1);
	}

	@InitBinder
	public void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(Type.class, new TypeConverter());
	}

}
