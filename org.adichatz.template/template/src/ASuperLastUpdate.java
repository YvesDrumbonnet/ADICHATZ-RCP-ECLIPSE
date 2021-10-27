package #{adichatz.package.name};

import java.util.Date;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrePersist;

@MappedSuperclass
public abstract class ASuperLastUpdate {
	@PreUpdate @PrePersist
	private void onUpdate() {
		setLastUpdate(new Date());
	}
	
    public abstract void setLastUpdate(Date lastUpdate);
}
