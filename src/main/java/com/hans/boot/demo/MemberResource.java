package com.hans.boot.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hans.boot.demo.mode.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class MemberResource {

    @Autowired
    private ObjectMapper objectMapper;

    private static List<Member> members = new ArrayList<>();

    private void addNecessaryHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, X-Auth-Token");
    }

    static {
        Member m1 = new Member(111, "Mr. M1");
        Member m2 = new Member(112, "Mr. M2");
        Member m3 = new Member(113, "Mr. M3");
        Member m4 = new Member(114, "Mr. M4");
        Member m5 = new Member(115, "Mr. M5");

        members.add(m1);
        members.add(m2);
        members.add(m3);
        members.add(m4);
        members.add(m5);
    }

    @RequestMapping("/api/members")
    public List<Member> get(HttpServletResponse response) {
        addNecessaryHeaders(response);
        return members;
    }

    @RequestMapping("/api/members/{id}")
    public Member getById(@PathVariable("id") long id, HttpServletResponse response) {
        addNecessaryHeaders(response);
            Optional<Member> result = members.stream().filter(m -> m.getId() == id).findAny();
            if(result.isPresent()) {
                return result.get();
            }else{
                return null;
            }
    }

    @RequestMapping("/api/members/search")
    public List<Member> get(@RequestParam("name") String name, HttpServletResponse response) {
        addNecessaryHeaders(response);
        List<Member> result = new ArrayList<>();
        members.forEach(m -> {
            if(m.getName().startsWith(name)) {
                result.add(m);
            }
        });
        return result;
    }

    @RequestMapping(value="/api/members", method = RequestMethod.POST)
    public Member add(@RequestBody Member member, HttpServletResponse response) {
        addNecessaryHeaders(response);
        long max = 0;
        for(Member m: members) {
            if(m.getId() > max) {
                max = m.getId();
            }
        }
        max += 1;
        Member result = new Member(max, member.getName());
        members.add(result);
        return result;
    }

    @RequestMapping(value="/api/members", method = RequestMethod.PUT)
    public Member update(@RequestBody Member member, HttpServletResponse response) {
        addNecessaryHeaders(response);
        Member result = this.getById(member.getId(), response);
        if(result != null) {
            members.remove(result);
            result.setName(member.getName());
        }
        members.add(result);
        return result;
    }

    @RequestMapping(value="/api/members/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") long id, HttpServletResponse response) {
        addNecessaryHeaders(response);
        Member result = this.getById(id, response);
        if(result != null) {
            members.remove(result);
        }
        return;
    }

}
