package #{adichatz.package.name};

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class ASuperLastUpdate {
	@PreUpdate @PrePersist
	private void onUpdate() {
		setLastUpdate(new Date());
	}
	
    public abstract void setLastUpdate(Date lastUpdate);
}
