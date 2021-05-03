package com.beanframework.imex.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.imex.ImexConstants;
import com.beanframework.imex.ImexType;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = ImexConstants.Table.IMEX)
public class Imex extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String TYPE = "type";
	public static final String DIRECTORY = "directory";
	public static final String FILENAME = "fileName";
	public static final String QUERY = "query";
	public static final String HEADER = "header";
	public static final String SEPERATOR = "seperator";
	public static final String MEDIAS = "medias";

	@Audited(withModifiedFlag = true)
	@Enumerated(EnumType.STRING)
	private ImexType type;

	@Audited(withModifiedFlag = true)
	private String directory;

	@Audited(withModifiedFlag = true)
	private String fileName;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 10000)
	private String query;

	@Audited(withModifiedFlag = true)
	@Lob
	@Column(length = 10000)
	private String header;

	@Audited(withModifiedFlag = true)
	private String seperator;

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = ImexConstants.Table.IMEX_MEDIA_REL, joinColumns = @JoinColumn(name = "imex_uuid"))
	@Column(name = "media_uuid", columnDefinition = "BINARY(16)", nullable = false)
	private Set<UUID> medias = new HashSet<UUID>();

	public ImexType getType() {
		return type;
	}

	public void setType(ImexType type) {
		this.type = type;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSeperator() {
		return seperator;
	}

	public void setSeperator(String seperator) {
		this.seperator = seperator;
	}

	public Set<UUID> getMedias() {
		return medias;
	}

	public void setMedias(Set<UUID> medias) {
		this.medias = medias;
	}

}
