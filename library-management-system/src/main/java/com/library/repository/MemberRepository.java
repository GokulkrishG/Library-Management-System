package com.library.repository;

import com.library.model.Member;
import com.library.exception.MemberNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member findById(Long id) {
        String sql = "SELECT id, name, phone, registered_date FROM member WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Member(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getDate("registered_date").toLocalDate()
                    ));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
    }

    public List<Member> findAll() {
        String sql = "SELECT id, name, phone, registered_date FROM member";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Member(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getDate("registered_date").toLocalDate()
                ));
    }

    public void save(Member member) {
        String sql = "INSERT INTO member (name, phone, registered_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getName(), member.getPhone(), member.getRegisteredDate());
    }

    public void update(Long id, Member member) {
        String sql = "UPDATE member SET name = ?, phone = ?, registered_date = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, member.getName(), member.getPhone(), member.getRegisteredDate(), id);
        if (rowsAffected == 0) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM member WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
    }
}