package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

public interface TagToCertificateRepository extends CrudRepository<Tag> {

    void addTagToCertificate(Long certificatedId, Tag tag);

    void deleteTagFromCertificate(Long certificatedId, Tag tag);

}
