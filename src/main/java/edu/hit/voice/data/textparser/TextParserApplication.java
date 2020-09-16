package edu.hit.voice.data.textparser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class TextParserApplication {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    public static final Pattern pattern = Pattern.compile("yyyy/MM/dd HH:mm:ss");
    public static final Predicate<String[]> LENTH_PREDICATE = x -> x.length == 5;


    public static void main(String[] args) {
        SpringApplication.run(TextParserApplication.class, args);

        try {
            File csvFile = ResourceUtils.getFile("classpath:voice_text.csv");
            InputStream is = new FileInputStream(csvFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            Map<String, List<Sentence>> idListMap = br.lines().skip(1)
                    .map(splitSentence())
                    .filter(LENTH_PREDICATE)
                    .map(buildSentence()).collect(Collectors.groupingBy(Sentence::getDialogId));
            System.out.println(idListMap.size());
            br.close();
        } catch (IOException e) {
            log.info("Error message: {} ", e.getMessage());
        }
    }

    private static Function<String, String[]> splitSentence() {
        return s -> s.split("\t");
    }

    private static Function<String[], Sentence> buildSentence() {
        return s -> Sentence.builder().dialogId(s[0])
                .newReply(s[1])
                .newClient(s[2])
                .replyTime(LocalDateTime.parse(s[3], formatter))
                .queryTime(LocalDateTime.parse(s[4], formatter)).build();
    }

}
