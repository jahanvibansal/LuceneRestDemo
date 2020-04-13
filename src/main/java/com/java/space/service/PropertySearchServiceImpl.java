package com.java.space.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.springframework.stereotype.Service;

import com.java.space.dto.Property.Category;
import com.java.space.dto.Property.Purpose;
import com.java.space.dto.Property.Timings;
import com.java.space.dto.Property.Type;
import com.java.space.dto.PropertyDetails.Availability;

@Service
public class PropertySearchServiceImpl implements PropertySearchService  {
	
	//Search properties by combination of type, purpose and city. Each can expect 2 typos
	 public  List<Document> searchByTypePurposeAndCity(Directory index, Type type, Purpose purpose, String city) throws IOException, ParseException {
		 IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
			Term t1= new Term("Type", type.name());
			Term t2= new Term("Purpose", purpose.name());
			Term t3= new Term("City", city);
			Query q1=new FuzzyQuery(t1,2);//raw search query
			Query q2=new FuzzyQuery(t2,2);
			Query q3= new FuzzyQuery(t3,2);
			BooleanClause c1= new BooleanClause(q1, Occur.MUST);
			BooleanClause c2= new BooleanClause(q2, Occur.MUST);
			BooleanClause c3= new BooleanClause(q3, Occur.MUST);
			BooleanQuery q= new BooleanQuery.Builder().add(c1).add(c2).add(c3).build();
			
			TopDocs docs=searcher.search(q, 10);
			List<Document> list= Arrays.stream(docs.scoreDocs).map(x-> {
				try {
					return searcher.doc(x.doc);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).collect(Collectors.toList());
			return list;
	 }
	 

		//Search properties by combination of type, purpose and city. Each can expect 2 typos
	 public  List<Document> searchByTimings(Directory index, Timings timing) throws IOException, ParseException {
		 IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
			Term t1= new Term("timing", timing.name());
			Query q1=new FuzzyQuery(t1,2);//raw search query
			TopDocs docs=searcher.search(q1, 10);
			return Arrays.stream(docs.scoreDocs).map(x-> {
				try {
					return searcher.doc(x.doc);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).collect(Collectors.toList());
	 }
	 public  List<Document> searchBySpaceNameOrTitle(Directory index, String text) throws IOException, ParseException {
			IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
				BooleanClause c1= new BooleanClause(new QueryParser("title",new StandardAnalyzer()).parse(text), Occur.MUST);
				BooleanClause c2= new BooleanClause(new QueryParser("description",new StandardAnalyzer()).parse(text), Occur.SHOULD);
				BooleanQuery q= new BooleanQuery.Builder().add(c1).add(c2).build();
				TopDocs docs=searcher.search(q, 10);
			return Arrays.stream(docs.scoreDocs).map(x-> {
				try {
					return searcher.doc(x.doc);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).collect(Collectors.toList());
		}


		public  List<Document>  searchByAvailability(Directory index, Availability availability) throws IOException, ParseException {
			IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
			Query q=new TermQuery(new Term("availabiltiy", availability.name()));//raw search query
			TopDocs docs=searcher.search(q, 10);
			return Arrays.stream(docs.scoreDocs).map(x-> {
				try {
					return searcher.doc(x.doc);
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}).collect(Collectors.toList());
		}
		
		public  List<Document>  searchByPopularity(Directory index, int popularity) throws IOException, ParseException {
			IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
			 Sort sort = new Sort(SortField.FIELD_SCORE,
	                    new SortField("popularity", SortField.Type.INT,true));
			 //true here represents reverse order
			 Term term = new Term("purpose", Purpose.RENT.name());
		        TermQuery termQuery = new TermQuery(term);
			 TopDocs docs = searcher.search(termQuery, 10, sort,true, false);
			 return Arrays.stream(docs.scoreDocs).map(x-> {
					try {
						return searcher.doc(x.doc);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}).collect(Collectors.toList());
		}
		
		public  List<Document>  searchByCategory(Directory index, Category category) throws IOException, ParseException {
			IndexReader reader= DirectoryReader.open(index);
			IndexSearcher searcher=new IndexSearcher(reader);
			
			 Term term = new Term("category", category.name());
		        TermQuery termQuery = new TermQuery(term);
			 TopDocs docs = searcher.search(termQuery,10);
	 
			 return Arrays.stream(docs.scoreDocs).map(x-> {
					try {
						return searcher.doc(x.doc);
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				}).collect(Collectors.toList());
		}
}
