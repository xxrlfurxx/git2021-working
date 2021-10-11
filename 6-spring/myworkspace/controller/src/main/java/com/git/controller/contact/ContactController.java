package com.git.controller.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	private SortedMap<Long, Contact> contacts = Collections
			.synchronizedSortedMap(
					new TreeMap<Long, Contact>(Collections.reverseOrder()));

	private AtomicLong maxId = new AtomicLong();

	@GetMapping(value = "/contacts")
	public List<Contact> getContacts() {

		return new ArrayList<Contact>(contacts.values());
	}

	@PostMapping(value = "/contacts")
	public Contact addContact(@RequestBody Contact contact,
			HttpServletResponse res) {

		if (contact.getName() == null || contact.getName().isEmpty()
				|| contact.getPhone() == null || contact.getPhone().isEmpty()
				|| contact.getEmail() == null || contact.getEmail().isEmpty()) {

			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;

		}

		Long currentId = maxId.incrementAndGet();

		Contact contactItem = Contact.builder().id(currentId)
				.name(contact.getName()).phone(contact.getPhone())
				.email(contact.getEmail()).createdTime(new Date().getTime())
				.build();

		contacts.put(currentId, contactItem);

		res.setStatus(HttpServletResponse.SC_CREATED);

		return contactItem;
	}

	@DeleteMapping(value = "/contacts/{id}")
	public boolean removeContact(@PathVariable long id,
			HttpServletResponse res) {

		Contact contact = contacts.get(Long.valueOf(id));

		if (contact == null) {

			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return false;
		}

		contacts.remove(Long.valueOf(id));

		return false;
	}

	@PutMapping(value = "/contacts/{id}")
	public Contact modifyContact(@PathVariable long id,
			@RequestBody Contact contact, HttpServletResponse res) {

		Contact findItem = contacts.get(Long.valueOf(id));

		if (findItem == null) {

			res.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		if (contact.getName() == null || contact.getName().isEmpty()
				|| contact.getPhone() == null || contact.getPhone().isEmpty()
				|| contact.getEmail() == null || contact.getEmail().isEmpty()) {

			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		findItem.setName(contact.getName());
		findItem.setPhone(contact.getPhone());
		findItem.setEmail(contact.getEmail());

		return findItem;
	}

}
