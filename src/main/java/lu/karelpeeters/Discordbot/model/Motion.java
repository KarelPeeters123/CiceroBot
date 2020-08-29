package lu.karelpeeters.Discordbot.model;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.time.OffsetDateTime;

public class Motion implements Comparable<Motion>{
	private long id;
	private String content;
	private String author;
	private OffsetDateTime timestamp;
	private String status;

	public Motion(MessageReceivedEvent event) {
		this.id = MotionRepository.getNumberOfMotions() + 1;
		this.content = event.getMessage().getContentDisplay().split(" ", 2)[1];
		this.author = event.getAuthor().getAsTag();
		this.timestamp = event.getMessage().getTimeCreated();
		this.status = "not resolved";
	}

	public Motion(long id, String content, String author, OffsetDateTime timestamp, String status) {
		this.id = id;
		this.content = content;
		this.author = author;
		this.timestamp = timestamp;
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public OffsetDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(@NotNull Motion o) {
		return Long.compare(this.getId(), o.getId());
	}
}
