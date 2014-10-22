package bupt.sc.neutron.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDemand {
	public static final Integer PERFORMANCE_FIRST = 0;
	public static final Integer ENERGY_FIRST = 1;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private int audioCap;
	private int mediaCap;
	private int strategy;
	
	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAudioCap() {
		return audioCap;
	}
	public void setAudioCap(int audioCap) {
		this.audioCap = audioCap;
	}
	public int getMediaCap() {
		return mediaCap;
	}
	public void setMediaCap(int mediaCap) {
		this.mediaCap = mediaCap;
	}
	public int getStrategy() {
		return strategy;
	}
	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}
}
