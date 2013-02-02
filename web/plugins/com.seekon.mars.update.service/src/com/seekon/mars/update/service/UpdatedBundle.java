package com.seekon.mars.update.service;

import java.io.Serializable;

public class UpdatedBundle implements Serializable {

	private static final long serialVersionUID = -1962418137607457481L;

	private String bundleName;

	private String bundleVersion;

	private long LastModifiedTime;

	public UpdatedBundle(String bundleName, String bundleVersion,
			long lastModifiedTime) {
		super();
		this.bundleName = bundleName;
		this.bundleVersion = bundleVersion;
		LastModifiedTime = lastModifiedTime;
	}

	public String getBundleName() {
		return bundleName;
	}

	public String getBundleVersion() {
		return bundleVersion;
	}

	public long getLastModifiedTime() {
		return LastModifiedTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (LastModifiedTime ^ (LastModifiedTime >>> 32));
		result = prime * result
				+ ((bundleName == null) ? 0 : bundleName.hashCode());
		result = prime * result
				+ ((bundleVersion == null) ? 0 : bundleVersion.hashCode());
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
		UpdatedBundle other = (UpdatedBundle) obj;
		if (LastModifiedTime != other.LastModifiedTime)
			return false;
		if (bundleName == null) {
			if (other.bundleName != null)
				return false;
		} else if (!bundleName.equals(other.bundleName))
			return false;
		if (bundleVersion == null) {
			if (other.bundleVersion != null)
				return false;
		} else if (!bundleVersion.equals(other.bundleVersion))
			return false;
		return true;
	}

}
