package com.seekon.smartclient.loader;

import java.io.Serializable;
import java.util.jar.Attributes;

public class JarFile implements Serializable {

	private static final long serialVersionUID = 267498329437074241L;

	private String name;

	private String location;

	private long lastModifiedTime;

	private Attributes manifest;

	public JarFile(String name, long lastModifiedTime) {
		super();
		this.name = name;
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(long lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Attributes getManifest() {
		return manifest;
	}

	public void setManifest(Attributes manifest) {
		this.manifest = manifest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (lastModifiedTime ^ (lastModifiedTime >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JarFile other = (JarFile) obj;
		if (lastModifiedTime != other.lastModifiedTime)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
