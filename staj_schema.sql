--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Homebrew)
-- Dumped by pg_dump version 17.5 (Homebrew)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: accountserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA accountserviceschema;


ALTER SCHEMA accountserviceschema OWNER TO meteey;

--
-- Name: authserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA authserviceschema;


ALTER SCHEMA authserviceschema OWNER TO meteey;

--
-- Name: balanceserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA balanceserviceschema;


ALTER SCHEMA balanceserviceschema OWNER TO meteey;

--
-- Name: cardserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA cardserviceschema;


ALTER SCHEMA cardserviceschema OWNER TO meteey;

--
-- Name: logserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA logserviceschema;


ALTER SCHEMA logserviceschema OWNER TO meteey;

--
-- Name: transactionserviceschema; Type: SCHEMA; Schema: -; Owner: meteey
--

CREATE SCHEMA transactionserviceschema;


ALTER SCHEMA transactionserviceschema OWNER TO meteey;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: account_contact; Type: TABLE; Schema: accountserviceschema; Owner: meteey
--

CREATE TABLE accountserviceschema.account_contact (
    account_id uuid NOT NULL,
    msisdn character varying(255),
    email character varying(255)
);


ALTER TABLE accountserviceschema.account_contact OWNER TO meteey;

--
-- Name: account_eula; Type: TABLE; Schema: accountserviceschema; Owner: meteey
--

CREATE TABLE accountserviceschema.account_eula (
    account_id uuid NOT NULL,
    eula_id uuid,
    eula_signed_at timestamp(6) with time zone
);


ALTER TABLE accountserviceschema.account_eula OWNER TO meteey;

--
-- Name: account_kyc; Type: TABLE; Schema: accountserviceschema; Owner: meteey
--

CREATE TABLE accountserviceschema.account_kyc (
    account_id uuid NOT NULL,
    national_id character varying(255),
    national_id_type character varying(255),
    kyc_type character varying(255),
    is_exceptional boolean,
    exceptional_desc character varying(255)
);


ALTER TABLE accountserviceschema.account_kyc OWNER TO meteey;

--
-- Name: account_profile; Type: TABLE; Schema: accountserviceschema; Owner: meteey
--

CREATE TABLE accountserviceschema.account_profile (
    account_id uuid NOT NULL,
    name character varying(255),
    surname character varying(255)
);


ALTER TABLE accountserviceschema.account_profile OWNER TO meteey;

--
-- Name: accounts; Type: TABLE; Schema: accountserviceschema; Owner: meteey
--

CREATE TABLE accountserviceschema.accounts (
    account_id uuid NOT NULL,
    user_id uuid NOT NULL,
    status character varying(255) DEFAULT 'ACTIVE'::text NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL
);


ALTER TABLE accountserviceschema.accounts OWNER TO meteey;

--
-- Name: refresh_tokens; Type: TABLE; Schema: authserviceschema; Owner: meteey
--

CREATE TABLE authserviceschema.refresh_tokens (
    id uuid NOT NULL,
    user_id uuid NOT NULL,
    expires_at timestamp(6) with time zone NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    revoked boolean DEFAULT false NOT NULL
);


ALTER TABLE authserviceschema.refresh_tokens OWNER TO meteey;

--
-- Name: users; Type: TABLE; Schema: authserviceschema; Owner: meteey
--

CREATE TABLE authserviceschema.users (
    id uuid NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    role character varying(255) NOT NULL,
    is_active boolean DEFAULT false NOT NULL,
    created_at timestamp(6) with time zone NOT NULL,
    updated_at timestamp(6) with time zone
);


ALTER TABLE authserviceschema.users OWNER TO meteey;

--
-- Name: balance; Type: TABLE; Schema: balanceserviceschema; Owner: meteey
--

CREATE TABLE balanceserviceschema.balance (
    accountid uuid,
    balanceblockid uuid NOT NULL,
    creationdate date,
    amount double precision
);


ALTER TABLE balanceserviceschema.balance OWNER TO meteey;

--
-- Name: accountlog; Type: TABLE; Schema: logserviceschema; Owner: meteey
--

CREATE TABLE logserviceschema.accountlog (
    log_id uuid NOT NULL,
    account_id uuid,
    ip text,
    log_time date
);


ALTER TABLE logserviceschema.accountlog OWNER TO meteey;

--
-- Name: authlog; Type: TABLE; Schema: logserviceschema; Owner: meteey
--

CREATE TABLE logserviceschema.authlog (
    log_id uuid NOT NULL,
    user_id uuid,
    ip text,
    log_time date
);


ALTER TABLE logserviceschema.authlog OWNER TO meteey;

--
-- Name: balancelog; Type: TABLE; Schema: logserviceschema; Owner: meteey
--

CREATE TABLE logserviceschema.balancelog (
    log_id uuid NOT NULL,
    account_id uuid,
    balance_block_id uuid,
    ip text,
    log_time date
);


ALTER TABLE logserviceschema.balancelog OWNER TO meteey;

--
-- Name: transactionlog; Type: TABLE; Schema: logserviceschema; Owner: meteey
--

CREATE TABLE logserviceschema.transactionlog (
    log_id uuid NOT NULL,
    ip text,
    transaction_id uuid,
    log_time date
);


ALTER TABLE logserviceschema.transactionlog OWNER TO meteey;

--
-- Name: periodictransaction; Type: TABLE; Schema: transactionserviceschema; Owner: meteey
--

CREATE TABLE transactionserviceschema.periodictransaction (
    status text,
    transactiontype text,
    amount double precision,
    sender_id uuid,
    transaction_id uuid NOT NULL,
    receiver_id uuid,
    transactiondate date,
    transactionperiod integer
);


ALTER TABLE transactionserviceschema.periodictransaction OWNER TO meteey;

--
-- Name: transactions; Type: TABLE; Schema: transactionserviceschema; Owner: meteey
--

CREATE TABLE transactionserviceschema.transactions (
    transaction_id uuid NOT NULL,
    sender_id uuid,
    receiver_id uuid,
    amount double precision,
    transactiontype text,
    transactiondate date,
    status text
);


ALTER TABLE transactionserviceschema.transactions OWNER TO meteey;

--
-- Data for Name: account_contact; Type: TABLE DATA; Schema: accountserviceschema; Owner: meteey
--

COPY accountserviceschema.account_contact (account_id, msisdn, email) FROM stdin;
73ccbbc2-630f-47a9-b6bd-fb4c6f299f42	+905444444444	test1@turkcell.com
\.


--
-- Data for Name: account_eula; Type: TABLE DATA; Schema: accountserviceschema; Owner: meteey
--

COPY accountserviceschema.account_eula (account_id, eula_id, eula_signed_at) FROM stdin;
73ccbbc2-630f-47a9-b6bd-fb4c6f299f42	a3e2c509-dc94-4126-b7b2-ec0de9be4b2b	2025-07-28 16:00:00+03
\.


--
-- Data for Name: account_kyc; Type: TABLE DATA; Schema: accountserviceschema; Owner: meteey
--

COPY accountserviceschema.account_kyc (account_id, national_id, national_id_type, kyc_type, is_exceptional, exceptional_desc) FROM stdin;
73ccbbc2-630f-47a9-b6bd-fb4c6f299f42	12345678901	TC	STANDARD	f	\N
\.


--
-- Data for Name: account_profile; Type: TABLE DATA; Schema: accountserviceschema; Owner: meteey
--

COPY accountserviceschema.account_profile (account_id, name, surname) FROM stdin;
73ccbbc2-630f-47a9-b6bd-fb4c6f299f42	TurkcellTest1	TurkcellTest1
\.


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: accountserviceschema; Owner: meteey
--

COPY accountserviceschema.accounts (account_id, user_id, status, created_at, updated_at) FROM stdin;
a44158d2-9228-4c4e-afe7-8115f12330e2	774bf95f-146c-4030-b9b0-0105c283d4ec	PENDING	2025-07-29 14:01:52.443983+03	2025-07-29 14:01:52.443983+03
73ccbbc2-630f-47a9-b6bd-fb4c6f299f42	dda19c0f-9e50-45d9-ac5e-432790cfac92	ACTIVE	2025-07-29 14:57:15.318414+03	2025-07-29 15:00:35.654836+03
\.


--
-- Data for Name: refresh_tokens; Type: TABLE DATA; Schema: authserviceschema; Owner: meteey
--

COPY authserviceschema.refresh_tokens (id, user_id, expires_at, created_at, revoked) FROM stdin;
00a22368-ccf8-427b-b040-6f4eeda653ee	ed9e2a88-dbd9-42d8-bce5-012fe5578510	2025-08-01 00:48:46.26909+03	2025-07-17 00:48:46.269089+03	t
bdff073d-1b52-488c-bdb4-3f52dedfba43	ed9e2a88-dbd9-42d8-bce5-012fe5578510	2025-08-01 02:00:05.555047+03	2025-07-17 02:00:05.555047+03	f
78075dbb-9359-44ee-887f-b4a3227e5e87	80203dde-e7da-4a63-879e-f5e5a4be5325	2025-08-01 12:49:46.918139+03	2025-07-17 12:49:46.918138+03	f
bdb03378-a094-4c57-a5f3-4054dbefc445	68d4b490-09a2-4b7a-8379-908facc25161	2025-08-01 13:48:33.480962+03	2025-07-17 13:48:33.480961+03	t
0c92550b-5e89-41d0-9c2c-d8e28f4111f9	68d4b490-09a2-4b7a-8379-908facc25161	2025-08-01 13:58:24.179285+03	2025-07-17 13:58:24.179284+03	f
f197e61a-c435-42d2-91ec-21f1b637340b	2708a4e9-b1b2-4c8f-8c3a-a787997b5290	2025-08-06 10:43:56.313679+03	2025-07-22 10:43:56.313678+03	f
4864cc3b-e759-49c1-b1f8-09f8979b79ec	f3ca1c7b-5a4f-4d23-9691-412f99fe76fc	2025-08-06 11:56:10.836805+03	2025-07-22 11:56:10.836804+03	f
7701e722-d51c-45f0-a2b5-46fb716cb8fa	ddb1ab03-be0f-4d48-8b66-751a09368b88	2025-08-06 12:00:47.185159+03	2025-07-22 12:00:47.185159+03	f
8ebd9d72-1902-4039-ad65-749853079633	e9a5b732-ba82-44cc-8994-01363ccb26b5	2025-08-06 12:02:51.947295+03	2025-07-22 12:02:51.947294+03	f
7fb13aa9-0329-43d0-9cd8-27a978156565	5364903c-1db9-494b-8df1-6926f2b07117	2025-08-06 12:05:56.741758+03	2025-07-22 12:05:56.741758+03	f
f5b17e5e-0927-4519-8f5f-ff017f337c9f	075a9c21-1ae6-4a95-8fc7-3689f36a361d	2025-08-06 12:07:45.331855+03	2025-07-22 12:07:45.331855+03	f
056ac9fb-8723-4ebd-b969-9e9647d5dc8e	a3d39545-c8ce-4dfc-b3b0-69067d01aa15	2025-08-06 12:08:35.596304+03	2025-07-22 12:08:35.596303+03	f
a2e4735c-fef5-4a85-a5bd-cfe4e45936b8	c1012369-edbf-43c1-a1be-3114ce01fa57	2025-08-12 12:20:27.156554+03	2025-07-28 12:20:27.156553+03	f
42fe6657-9a21-46b1-a15d-06b65a06058b	774bf95f-146c-4030-b9b0-0105c283d4ec	2025-08-13 14:01:52.368219+03	2025-07-29 14:01:52.368218+03	f
f89c99fc-48b0-48b2-a326-e23361752ef0	dda19c0f-9e50-45d9-ac5e-432790cfac92	2025-08-13 14:57:15.216151+03	2025-07-29 14:57:15.216151+03	f
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: authserviceschema; Owner: meteey
--

COPY authserviceschema.users (id, email, password, role, is_active, created_at, updated_at) FROM stdin;
ed9e2a88-dbd9-42d8-bce5-012fe5578510	meteey@gmail.com	$2a$10$4uXEu9EzRU0akOyf41iIwuZ597u0cD6xJHWuB0o0A7gLrxs4r5/ga		t	2025-07-17 00:48:46.2571+03	2025-07-17 02:00:05.552798+03
80203dde-e7da-4a63-879e-f5e5a4be5325	meteey@tschisch.com	$2a$10$01HeoHXKIb7qaAL9/Pmo5uzA8sk1vemxOBhw8V/riePP9qvRIRxW.	USER	t	2025-07-17 12:49:46.901232+03	2025-07-17 13:17:33.121047+03
68d4b490-09a2-4b7a-8379-908facc25161	konurmustafa17@gmail.com	$2a$10$AvT9Ag3YhHYMqfjC7/3s..JpNwS11fEZ3Q4FkpMRCJ8Ycqdd37G1y	USER	t	2025-07-17 13:48:33.464327+03	2025-07-17 13:58:24.172876+03
2708a4e9-b1b2-4c8f-8c3a-a787997b5290	meteey123@gmail.com	$2a$10$ePwDXE2EgiixeZNXePLuHe6.2mdQJnYplFaE/4jjf0fm5XqfaQoFu	USER	t	2025-07-22 10:43:56.292481+03	2025-07-22 10:43:56.292483+03
f3ca1c7b-5a4f-4d23-9691-412f99fe76fc	yeetem@gmail.com	$2a$10$CbHXSd/4UgvJsXTt60Qm5ujEtZu2KyzFS9o.FuSX6jboHdZ46FE06	USER	t	2025-07-22 11:56:10.816683+03	2025-07-22 11:56:10.816684+03
ddb1ab03-be0f-4d48-8b66-751a09368b88	yeet@gmail.com	$2a$10$u6SXaDQWQY3RPkJa6yH.MuDg0ThdV.E7CKVYnBkZcP4oqwIdloQpm	USER	t	2025-07-22 12:00:47.15368+03	2025-07-22 12:00:47.153681+03
e9a5b732-ba82-44cc-8994-01363ccb26b5	yt@gmail.com	$2a$10$aAakZcTHpOu.4KRePUuAk.eXrYSWuiLtyjjA4FDZwDEVCDOEBg/lG	USER	t	2025-07-22 12:02:51.944729+03	2025-07-22 12:02:51.944729+03
5364903c-1db9-494b-8df1-6926f2b07117	y@gmail.com	$2a$10$DwcF6oj1TvHPCmeJQHQhReEqmFWzc3pWrCHZGYYtgVwfIBrbDwfOe	USER	t	2025-07-22 12:05:56.738951+03	2025-07-22 12:05:56.738952+03
075a9c21-1ae6-4a95-8fc7-3689f36a361d	ytt@gmail.com	$2a$10$Cfiv8OGLQwaLlS.qqOVPP.4a/o4952cYIege6Zp6X8OcMr.aYSXkK	USER	t	2025-07-22 12:07:45.328363+03	2025-07-22 12:07:45.328364+03
a3d39545-c8ce-4dfc-b3b0-69067d01aa15	yttttt@gmail.com	$2a$10$G2zZtXjWYTqS2EKvle8kBO4O0TBoPu7Gu4xY/RwTohxy0Hzrn.GDG	USER	t	2025-07-22 12:08:35.592592+03	2025-07-22 12:08:35.592592+03
c1012369-edbf-43c1-a1be-3114ce01fa57	hilalsevili@icloud.com	$2a$10$5YHij96oiJ4GV95UKGagoOklbKuTHAEqhdCp4r2ZlnnTUgqXz45V6	USER	t	2025-07-28 12:20:27.150004+03	2025-07-28 12:20:27.150005+03
774bf95f-146c-4030-b9b0-0105c283d4ec	turkcelltest	$2a$10$DyexPYxNa15To6JTCPREl.z5gIFekhFvfypyTZSvdJ5Q9Yf5GYodC	USER	t	2025-07-29 14:01:52.350779+03	2025-07-29 14:01:52.35078+03
dda19c0f-9e50-45d9-ac5e-432790cfac92	apigatewaytest	$2a$10$JjwSXPR.ANivRsPeSX9JDePZE6TvHVu1IPvzjD.abUGQLTBx.lPUC	USER	t	2025-07-29 14:57:15.196825+03	2025-07-29 14:57:15.196826+03
\.


--
-- Data for Name: balance; Type: TABLE DATA; Schema: balanceserviceschema; Owner: meteey
--

COPY balanceserviceschema.balance (accountid, balanceblockid, creationdate, amount) FROM stdin;
\.


--
-- Data for Name: accountlog; Type: TABLE DATA; Schema: logserviceschema; Owner: meteey
--

COPY logserviceschema.accountlog (log_id, account_id, ip, log_time) FROM stdin;
\.


--
-- Data for Name: authlog; Type: TABLE DATA; Schema: logserviceschema; Owner: meteey
--

COPY logserviceschema.authlog (log_id, user_id, ip, log_time) FROM stdin;
\.


--
-- Data for Name: balancelog; Type: TABLE DATA; Schema: logserviceschema; Owner: meteey
--

COPY logserviceschema.balancelog (log_id, account_id, balance_block_id, ip, log_time) FROM stdin;
\.


--
-- Data for Name: transactionlog; Type: TABLE DATA; Schema: logserviceschema; Owner: meteey
--

COPY logserviceschema.transactionlog (log_id, ip, transaction_id, log_time) FROM stdin;
\.


--
-- Data for Name: periodictransaction; Type: TABLE DATA; Schema: transactionserviceschema; Owner: meteey
--

COPY transactionserviceschema.periodictransaction (status, transactiontype, amount, sender_id, transaction_id, receiver_id, transactiondate, transactionperiod) FROM stdin;
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: transactionserviceschema; Owner: meteey
--

COPY transactionserviceschema.transactions (transaction_id, sender_id, receiver_id, amount, transactiontype, transactiondate, status) FROM stdin;
\.


--
-- Name: account_contact account_contact_pk; Type: CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_contact
    ADD CONSTRAINT account_contact_pk PRIMARY KEY (account_id);


--
-- Name: account_eula account_eula_pk; Type: CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_eula
    ADD CONSTRAINT account_eula_pk PRIMARY KEY (account_id);


--
-- Name: account_kyc account_kyc_pk; Type: CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_kyc
    ADD CONSTRAINT account_kyc_pk PRIMARY KEY (account_id);


--
-- Name: account_profile account_profile_pk; Type: CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_profile
    ADD CONSTRAINT account_profile_pk PRIMARY KEY (account_id);


--
-- Name: accounts accounts_pk; Type: CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.accounts
    ADD CONSTRAINT accounts_pk PRIMARY KEY (account_id);


--
-- Name: refresh_tokens refresh_tokens_pk; Type: CONSTRAINT; Schema: authserviceschema; Owner: meteey
--

ALTER TABLE ONLY authserviceschema.refresh_tokens
    ADD CONSTRAINT refresh_tokens_pk PRIMARY KEY (id);


--
-- Name: users users_pk; Type: CONSTRAINT; Schema: authserviceschema; Owner: meteey
--

ALTER TABLE ONLY authserviceschema.users
    ADD CONSTRAINT users_pk PRIMARY KEY (id);


--
-- Name: balance balance_pk; Type: CONSTRAINT; Schema: balanceserviceschema; Owner: meteey
--

ALTER TABLE ONLY balanceserviceschema.balance
    ADD CONSTRAINT balance_pk PRIMARY KEY (balanceblockid);


--
-- Name: accountlog accountlog_pk; Type: CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.accountlog
    ADD CONSTRAINT accountlog_pk PRIMARY KEY (log_id);


--
-- Name: authlog authlog_pk; Type: CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.authlog
    ADD CONSTRAINT authlog_pk PRIMARY KEY (log_id);


--
-- Name: balancelog balancelog_pk; Type: CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.balancelog
    ADD CONSTRAINT balancelog_pk PRIMARY KEY (log_id);


--
-- Name: transactionlog transactionlog_pk; Type: CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.transactionlog
    ADD CONSTRAINT transactionlog_pk PRIMARY KEY (log_id);


--
-- Name: periodictransaction ptransactions_pk; Type: CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.periodictransaction
    ADD CONSTRAINT ptransactions_pk PRIMARY KEY (transaction_id);


--
-- Name: transactions transactions_pk; Type: CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.transactions
    ADD CONSTRAINT transactions_pk PRIMARY KEY (transaction_id);


--
-- Name: account_contact account_contact_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_contact
    ADD CONSTRAINT account_contact_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: account_eula account_eula_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_eula
    ADD CONSTRAINT account_eula_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: account_kyc account_kyc_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_kyc
    ADD CONSTRAINT account_kyc_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: account_profile account_profile_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: accountserviceschema; Owner: meteey
--

ALTER TABLE ONLY accountserviceschema.account_profile
    ADD CONSTRAINT account_profile_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: refresh_tokens refresh_tokens_users_id_fk; Type: FK CONSTRAINT; Schema: authserviceschema; Owner: meteey
--

ALTER TABLE ONLY authserviceschema.refresh_tokens
    ADD CONSTRAINT refresh_tokens_users_id_fk FOREIGN KEY (user_id) REFERENCES authserviceschema.users(id) ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: balance balance_fk; Type: FK CONSTRAINT; Schema: balanceserviceschema; Owner: meteey
--

ALTER TABLE ONLY balanceserviceschema.balance
    ADD CONSTRAINT balance_fk FOREIGN KEY (accountid) REFERENCES accountserviceschema.accounts(account_id) ON UPDATE CASCADE;


--
-- Name: accountlog accountlog_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.accountlog
    ADD CONSTRAINT accountlog_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- Name: authlog authlog_users_id_fk; Type: FK CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.authlog
    ADD CONSTRAINT authlog_users_id_fk FOREIGN KEY (user_id) REFERENCES authserviceschema.users(id);


--
-- Name: balancelog balancelog_accounts_account_id_fk; Type: FK CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.balancelog
    ADD CONSTRAINT balancelog_accounts_account_id_fk FOREIGN KEY (account_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- Name: balancelog balancelog_balance_balanceblockid_fk; Type: FK CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.balancelog
    ADD CONSTRAINT balancelog_balance_balanceblockid_fk FOREIGN KEY (balance_block_id) REFERENCES balanceserviceschema.balance(balanceblockid);


--
-- Name: transactionlog transactionlog_transactions_transaction_id_fk; Type: FK CONSTRAINT; Schema: logserviceschema; Owner: meteey
--

ALTER TABLE ONLY logserviceschema.transactionlog
    ADD CONSTRAINT transactionlog_transactions_transaction_id_fk FOREIGN KEY (transaction_id) REFERENCES transactionserviceschema.transactions(transaction_id);


--
-- Name: periodictransaction preceiver_fk; Type: FK CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.periodictransaction
    ADD CONSTRAINT preceiver_fk FOREIGN KEY (receiver_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- Name: periodictransaction psender_fk; Type: FK CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.periodictransaction
    ADD CONSTRAINT psender_fk FOREIGN KEY (sender_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- Name: transactions receiver_fk; Type: FK CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.transactions
    ADD CONSTRAINT receiver_fk FOREIGN KEY (receiver_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- Name: transactions sender_fk; Type: FK CONSTRAINT; Schema: transactionserviceschema; Owner: meteey
--

ALTER TABLE ONLY transactionserviceschema.transactions
    ADD CONSTRAINT sender_fk FOREIGN KEY (sender_id) REFERENCES accountserviceschema.accounts(account_id);


--
-- PostgreSQL database dump complete
--

