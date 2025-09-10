-- Seed data for family_member_access
-- Assuming owners and users with these IDs already exist
INSERT INTO family_member_access (owner_id, member_user_id, invite_email, role, status, invite_token, invited_at)
VALUES
  (1, 2, 'member1@example.com', 'FULL_ACCESS', 'ACTIVE', NULL, CURRENT_TIMESTAMP),
  (1, 3, 'member2@example.com', 'EDIT', 'ACTIVE', NULL, CURRENT_TIMESTAMP),
  (1, NULL, 'pending@example.com', 'VIEW_ONLY', 'INVITED', 'SAMPLETOKEN123', CURRENT_TIMESTAMP);
