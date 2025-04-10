package com.theroom.server.service;

import com.theroom.server.domain.entity.Address;
import com.theroom.server.domain.entity.Contact;
import com.theroom.server.domain.entity.CustomerFile;
import com.theroom.server.domain.entity.ProcessStatus;
import com.theroom.server.domain.request.ContactAddRequest;
import com.theroom.server.domain.request.ContactModifyRequest;
import com.theroom.server.domain.response.ContactDetailResponse;
import com.theroom.server.domain.response.ContactResponse;
import com.theroom.server.domain.response.SimpleFile;
import com.theroom.server.repository.ContactRepository;
import com.theroom.server.util.LocalFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final LocalFileUtil localFileUtil;
    private final ContactRepository contactRepository;

    @Transactional
    public void addContact(ContactAddRequest request, List<MultipartFile> files) {
        List<CustomerFile> customerFiles = new ArrayList<>();

        if (files != null) {
            for (MultipartFile file : files) {
                CustomerFile customerFile = CustomerFile.builder()
                        .name(localFileUtil.saveFile(file))
                        .originalName(file.getOriginalFilename())
                        .size(file.getSize())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .type(file.getContentType())
                        .build();

                customerFiles.add(customerFile);
            }
        }

        Contact contact = Contact.builder()
                .customer(request.getCustomer())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .buildingType(request.getBuildingType())
                .exclusiveArea(request.getExclusiveArea())
                .budget(request.getBudget())
                .interiorType(request.getInteriorType())
                .startDate(request.getStartDate())
                .moveInDate(request.getMoveInDate())
                .processStatus(ProcessStatus.CONTACT)
                .address(Address.builder()
                        .mainAddress(request.getMainAddress())
                        .detailAddress(request.getDetailAddress())
                        .postCode(request.getPostCode())
                        .latitude(request.getLatitude())
                        .longitude(request.getLongitude())
                        .build()
                )
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .personalInformationAgree(true)
                .customerFiles(customerFiles)
                .memo("")
                .customerMemo(request.getCustomerMemo())
                .build();

        contactRepository.save(contact);
    }

    public List<ContactResponse> getList() {
        return contactRepository.findAllWithAddress()
                .stream()
                .map(c -> ContactResponse.builder()
                        .id(c.getId())
                        .customer(c.getCustomer())
                        .mainAddress(c.getAddress().getMainAddress())
                        .detailAddress(c.getAddress().getDetailAddress())
                        .status(c.getProcessStatus())
                        .build())
                .toList();
    }

    public ContactDetailResponse getContactDetail(Long contactId) {
        Contact contact = contactRepository.findByIdWithAll(contactId).orElseThrow();

        return ContactDetailResponse.builder()
                .customer(contact.getCustomer())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .buildingType(contact.getBuildingType())
                .exclusiveArea(Math.floor(contact.getExclusiveArea() * 10) / 10.0)
                .budget(contact.getBudget())
                .interiorType(contact.getInteriorType())
                .startDate(contact.getStartDate())
                .moveInDate(contact.getMoveInDate())
                .status(contact.getProcessStatus())
                .mainAddress(contact.getAddress().getMainAddress())
                .detailAddress(contact.getAddress().getDetailAddress())
                .postCode(contact.getAddress().getPostCode())
                .latitude(contact.getAddress().getLatitude())
                .longitude(contact.getAddress().getLongitude())
                .files(contact.getCustomerFiles()
                        .stream()
                        .map(c -> SimpleFile.builder()
                                .id(c.getId())
                                .name(c.getOriginalName())
                                .uploadedName(c.getName())
                                .size(c.getSize())
                                .build()
                        )
                        .toList()
                )
                .memo(contact.getMemo())
                .customerMemo(contact.getCustomerMemo())
                .build();
    }

    @Transactional
    public void modifyContact(Long contactId, ContactModifyRequest request) {
        Contact contact = contactRepository.findByIdWithAddress(contactId).orElseThrow();
        contact.modify(request);
    }

    @Transactional
    public void deleteContact(Long contactId) {
        Contact savedContact = contactRepository.findByIdWithFiles(contactId).orElseThrow();

        if (savedContact.getCustomerFiles() != null) {
            localFileUtil.deleteFiles(savedContact.getCustomerFiles());
        }

        contactRepository.deleteById(contactId);
    }
}
