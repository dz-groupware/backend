package com.example.backend.modal.service;

import com.example.backend.common.SingleResponseDto;
import com.example.backend.modal.dto.ProfileRes;
import com.example.backend.modal.dto.TreeItemRes;
import java.util.List;

public interface ModalService {
  List<ProfileRes> getProfileByUserId();
  List<TreeItemRes> getOrgTree(String type);

  List<ProfileRes> findEmpList(String type);

  SingleResponseDto<?> findOrgSearchResult(String type, String text);
}
