package run.onco.api.mongo.utils;

import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import com.mongodb.util.JSON;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@SuppressWarnings("deprecation")
public class MongoUtil {

	private static MongoClient mongo = null;
	private static MongoClientURI mongoClientURI = null;
	
	public static DBCollection getCollection(String collectionName) {
		return getDB(mongoClientURI.getDatabase()).getCollection(collectionName);
	}
	
	public static DB getDB(String dbName) {
		if (mongo == null) {
			throw new RuntimeException("Unable to connect to MongoDB " + dbName);
		}
		
		return mongo.getDB(dbName);
	}
	
	public static void initMongoClient(String uri) throws UnknownHostException {
		MongoClientOptions.Builder builder = MongoClientOptions.builder()
				.connectionsPerHost(2000)
				.threadsAllowedToBlockForConnectionMultiplier(500)
				.connectTimeout(5000)
				.maxWaitTime(120000)
				.socketTimeout(0)
				.readPreference(ReadPreference.secondaryPreferred())
				.writeConcern(WriteConcern.REPLICA_ACKNOWLEDGED)
				.socketKeepAlive(true)
				.minConnectionsPerHost(20);
		
		mongoClientURI = new MongoClientURI(uri, builder);
		mongo = new MongoClient(mongoClientURI, null);
	}
	
	public static void close() {
		if (mongo != null) {
			mongo.close();
		}
	}
	
	public DBCollection createCollection(final String collectionName) {
		boolean exists = collectionExists(collectionName);
		if (!exists) {
			return getDB(mongoClientURI.getDatabase()).createCollection(collectionName, null);
		}
		return getDB(mongoClientURI.getDatabase()).getCollection(collectionName);
	}
	
	public void insertDocument(final String collectionName, final String json) {
		DBCollection collection = createCollection(collectionName);
		DBObject dbObject = (DBObject)JSON.parse(json);
		collection.insert(dbObject);
	}
	
	private static boolean collectionExists(final String collectionName) {
		return getDB(mongoClientURI.getDatabase()).collectionExists(collectionName);
	}
}
