package core.support;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class DateTimeSerializer extends JsonSerializer<Date> {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(DateFormatUtils.format(value, DATE_FORMAT));
	}
	
	public String epochTimeToDateSerialize(Long epochTime) {
		Date date = new Date(epochTime); 
        DateFormat format = new SimpleDateFormat(DATE_FORMAT); 
        String formatted = format.format(date); 
        return formatted;
	}
	
	public static String DateToStringSerialize(Date date) {  
        DateFormat format = new SimpleDateFormat(DATE_FORMAT); 
        String formatted = format.format(date); 
        return formatted;
	}

}
