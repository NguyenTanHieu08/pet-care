package com.hieusrpingboot.petcare.controller;

import com.hieusrpingboot.petcare.dto.AcceptInviteRequest;
import com.hieusrpingboot.petcare.dto.ApiResponse;
import com.hieusrpingboot.petcare.dto.InviteRequest;
import com.hieusrpingboot.petcare.dto.UpdateMemberRoleRequest;
import com.hieusrpingboot.petcare.entity.FamilyMemberAccess;
import com.hieusrpingboot.petcare.service.FamilyAccessService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/family")
@CrossOrigin(origins = "*")
public class FamilySharingController extends BaseController {

    @Autowired
    private FamilyAccessService familyAccessService;

    @PostMapping("/invitations")
    public ResponseEntity<ApiResponse<FamilyMemberAccess>> invite(@Valid @RequestBody InviteRequest body,
                                                                  @RequestParam(required = false) Long memberUserId) {
        FamilyMemberAccess created = familyAccessService.invite(body, null, memberUserId);
        return success("Invitation created", created);
    }

    @PostMapping("/invitations/accept")
    public ResponseEntity<ApiResponse<FamilyMemberAccess>> accept(@Valid @RequestBody AcceptInviteRequest body,
                                                                  @RequestParam Long currentUserId) {
        FamilyMemberAccess updated = familyAccessService.accept(body, currentUserId);
        return success("Invitation accepted", updated);
    }

    @GetMapping("/members")
    public ResponseEntity<ApiResponse<List<FamilyMemberAccess>>> list(@RequestParam Long ownerId) {
        return success("OK", familyAccessService.listMembers(ownerId));
    }

    @PutMapping("/members/{id}/role")
    public ResponseEntity<ApiResponse<FamilyMemberAccess>> updateRole(@PathVariable Long id,
                                                                      @Valid @RequestBody UpdateMemberRoleRequest body) {
        return success("Role updated", familyAccessService.updateRole(id, body));
    }

    @DeleteMapping("/members/{id}")
    public ResponseEntity<ApiResponse<String>> revoke(@PathVariable Long id) {
        familyAccessService.revoke(id);
        return success("Revoked", "OK");
    }
}
