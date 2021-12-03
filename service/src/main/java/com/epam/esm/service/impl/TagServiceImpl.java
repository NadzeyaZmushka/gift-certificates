package com.epam.esm.service.impl;

import com.epam.esm.configuration.Translator;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.IncorrectDataException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.analytic.WidelyUsedTagStatistic;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.TagValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.exception.CustomErrorCode.PAGE_INCORRECT_CODE;
import static com.epam.esm.exception.CustomErrorCode.TAG_DUPLICATE_CODE;
import static com.epam.esm.exception.CustomErrorCode.TAG_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.PAGE_INCORRECT;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_DUPLICATE;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_ID_NOT_FOUND;
import static com.epam.esm.exception.ErrorMessageCodeConstant.TAG_WITH_NAME_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagValidator tagValidator;
    private final Translator translator;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        tagValidator.validTag(tag);
        if (tagRepository.findByName(tag.getName()).isPresent()) {
            throw new DuplicateException(translator.toLocale(TAG_DUPLICATE), TAG_DUPLICATE_CODE.getErrorCode());
        } else {
            return tagRepository.add(tag);
        }
    }

    @Override
    public List<Tag> findAll(int limit, int page) {
        if (page <= 0 || limit <= 0) {
            throw new IncorrectDataException(translator.toLocale(PAGE_INCORRECT), PAGE_INCORRECT_CODE.getErrorCode());
        }
        return tagRepository.findAll(page, limit);
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_ID_NOT_FOUND), id), TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tag tag = findById(id);
        tagRepository.remove(tag);
    }

    @Override
    public Tag findByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(() -> new NoSuchEntityException(String.format(translator.toLocale(TAG_WITH_NAME_NOT_FOUND), name),
                        TAG_NOT_FOUND.getErrorCode()));
    }

    @Override
    @Transactional
    public Tag findByNameOrCreate(String name) {
        return tagRepository.findByName(name).orElseGet(() -> {
            Tag tag = new Tag();
            tag.setName(name);
            add(tag);
            return tag;
        });
    }

    @Override
    public List<Tag> findByCertificateId(Long certificateId) {
        return tagRepository.findByCertificateId(certificateId);
    }

    @Override
    public List<Tag> findByNames(List<String> names) {
        return tagRepository.findByNames(names);
    }

    @Override
    public List<WidelyUsedTagStatistic> findWidelyUsed() {
        List<WidelyUsedTagStatistic> widelyUsedTagsByUserList = new ArrayList<>();
        var tagsByUserMap = tagRepository.findMostPopularTag().stream()
                .collect(Collectors.groupingBy(map -> Long.parseLong(map.get("userId"))));
        for (var item : tagsByUserMap.entrySet()) {
            int maxCount = item.getValue().stream()
                    .map(result -> result.get("count"))
                    .mapToInt(Integer::parseInt)
                    .max()
                    .getAsInt();
            WidelyUsedTagStatistic tagsByUser = new WidelyUsedTagStatistic();
            Set<Tag> tags = new HashSet<>();
            for (var analytic : item.getValue()) {
                if (Integer.parseInt(analytic.get("count")) == maxCount) {
                    tags.add(new Tag(Long.valueOf(analytic.get("tagId")), analytic.get("tagName")));
                }
                tagsByUser.setUserId(Long.parseLong(analytic.get("userId")));
            }
            tagsByUser.setTags(tags);
            widelyUsedTagsByUserList.add(tagsByUser);
        }
        return widelyUsedTagsByUserList;
    }

    @Override
    public Long count() {
        return tagRepository.count();
    }

}
