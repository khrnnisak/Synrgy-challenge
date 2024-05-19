--
-- PostgreSQL database dump
--

-- Dumped from database version 15.3
-- Dumped by pg_dump version 15.3

-- Started on 2024-05-06 00:17:14

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 3359 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 221 (class 1255 OID 342715)
-- Name: user_delete(uuid); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_delete(IN "ID" uuid)
    LANGUAGE sql
    AS $$
update users  set deleted_date = now() where id="ID";
$$;


ALTER PROCEDURE public.user_delete(IN "ID" uuid) OWNER TO postgres;

--
-- TOC entry 219 (class 1255 OID 342713)
-- Name: user_insert(character varying, character varying, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_insert(IN "Email_address" character varying, IN "Username" character varying, IN "Password" character varying)
    LANGUAGE sql
    AS $$
INSERT INTO public.users (id, email_addres , username , "password" , created_date, deleted_date, updated_date)
 VALUES (gen_random_uuid(), "Email_address", "Username", "Password", now(),  null, null);
$$;


ALTER PROCEDURE public.user_insert(IN "Email_address" character varying, IN "Username" character varying, IN "Password" character varying) OWNER TO postgres;

--
-- TOC entry 220 (class 1255 OID 342714)
-- Name: user_update(uuid, character varying); Type: PROCEDURE; Schema: public; Owner: postgres
--

CREATE PROCEDURE public.user_update(IN "ID" uuid, IN "NewPassword" character varying)
    LANGUAGE sql
    AS $$
update users  set password = "NewPassword", updated_date = now() where id="ID";
$$;


ALTER PROCEDURE public.user_update(IN "ID" uuid, IN "NewPassword" character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 214 (class 1259 OID 342660)
-- Name: merchant; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.merchant (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    is_opened boolean,
    merchant_location character varying(255),
    name character varying(255)
);


ALTER TABLE public.merchant OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 342667)
-- Name: order_detail; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_detail (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    payment character varying(255),
    quantity integer,
    total_price double precision,
    order_id uuid,
    product_id uuid,
    CONSTRAINT order_detail_payment_check CHECK (((payment)::text = ANY ((ARRAY['BINARCASH'::character varying, 'BINARPAY'::character varying, 'BINARPAYLATER'::character varying])::text[])))
);


ALTER TABLE public.order_detail OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 342673)
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    destination character varying(255),
    is_completed boolean,
    order_time date,
    user_id uuid
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 342678)
-- Name: product; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.product (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    category character varying(255),
    name character varying(255),
    price double precision,
    merchant_id uuid,
    CONSTRAINT product_category_check CHECK (((category)::text = ANY ((ARRAY['BAVERAGES'::character varying, 'DESSERTS'::character varying, 'FOODS'::character varying])::text[])))
);


ALTER TABLE public.product OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 342686)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    id uuid NOT NULL,
    created_date timestamp(6) without time zone,
    deleted_date timestamp(6) without time zone,
    updated_date timestamp(6) without time zone,
    email_addres character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 3349 (class 0 OID 342660)
-- Dependencies: 214
-- Data for Name: merchant; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.merchant (id, created_date, deleted_date, updated_date, is_opened, merchant_location, name) FROM stdin;
3efdca84-2f0d-4e3e-96b5-763b12a5bcc7	2024-05-04 23:10:24.525	2024-05-04 23:18:22.756392	2024-05-04 23:10:24.525	t	1	merchant
122f0364-9be9-459b-8f11-a06b7a718d20	2024-05-04 23:17:40.037	\N	2024-05-05 22:13:38.069	t	jakarta	mercen
\.


--
-- TOC entry 3350 (class 0 OID 342667)
-- Dependencies: 215
-- Data for Name: order_detail; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_detail (id, created_date, deleted_date, updated_date, payment, quantity, total_price, order_id, product_id) FROM stdin;
\.


--
-- TOC entry 3351 (class 0 OID 342673)
-- Dependencies: 216
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.orders (id, created_date, deleted_date, updated_date, destination, is_completed, order_time, user_id) FROM stdin;
402bce87-9b19-4f4f-8710-17a203489107	2024-05-04 11:11:26.743	2024-05-04 12:20:38.183849	2024-05-04 11:41:21.622	jakarta	f	2024-05-04	346aacbe-7c4e-4774-ad85-20a45a921d28
565ebcc9-e164-40c7-9409-a7d1cbff5721	2024-05-04 12:21:39.494	\N	2024-05-04 12:21:39.494	jakarta	f	2024-05-04	346aacbe-7c4e-4774-ad85-20a45a921d28
ca1edc9f-76ad-46ec-8ef0-d3c0aef2af9e	2024-05-05 06:35:57.486	\N	2024-05-05 06:35:57.486	malang	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
7b0235f9-1660-4e51-a551-0132d6815815	2024-05-05 07:45:49.717	2024-05-05 07:46:13.924701	2024-05-05 07:45:49.717	bali	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
83c9b6bb-2549-4911-8640-6e6f82d6ceee	2024-05-05 07:46:20.221	\N	2024-05-05 07:46:20.221	bali	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
fa646f2e-17a6-440b-a460-190a6b5961a4	2024-05-05 08:33:57.756	\N	2024-05-05 08:33:57.756	kalimantan	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
2b4c3b59-09cd-49f1-b62b-88adf26e1db4	2024-05-05 14:41:31.264	\N	2024-05-05 14:41:31.264	ppp	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
12e862a1-47cf-46f9-a550-ec5647be871a	2024-05-05 14:50:41.721	\N	2024-05-05 14:50:41.721	kkk	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
ecf9dc8e-cbf1-40f0-b293-21bb80b72d15	2024-05-05 14:51:45.801	\N	2024-05-05 14:51:45.801	lll	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
45ab0f4b-1002-41be-b3a6-4bdb3bcb78b7	2024-05-05 20:18:34.034	\N	2024-05-05 20:19:04.122	bhg	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
2d0f727e-f02c-48a4-849f-26e3a241a296	2024-05-05 20:22:44.976	\N	2024-05-05 20:22:44.976	eee	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
353e1fe0-0df4-4ec1-8cf7-26082c6d5003	2024-05-05 21:05:54.89	\N	2024-05-05 21:05:54.89	wer	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
6fdb596d-6005-4ca9-a2a9-f6ed19063781	2024-05-05 21:07:43.981	\N	2024-05-05 21:07:43.981	hiu	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
ede00f96-7aba-40e1-9783-82e9e473fb3e	2024-05-05 21:13:00.745	\N	2024-05-05 21:13:00.745	test	f	2024-05-05	346aacbe-7c4e-4774-ad85-20a45a921d28
9af24c67-e465-4e3a-ad93-40c136bc6131	2024-05-05 23:19:35.013	\N	2024-05-05 23:19:35.013	malang	f	2024-05-05	cd12a5ae-5da2-46e9-8f40-c899ba4f6bba
\.


--
-- TOC entry 3352 (class 0 OID 342678)
-- Dependencies: 217
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.product (id, created_date, deleted_date, updated_date, category, name, price, merchant_id) FROM stdin;
7e4bd14d-49cd-4674-b819-059258ba71df	2024-05-05 06:05:15.626	\N	2024-05-05 06:05:15.626	FOODS	mi goreng	12000	122f0364-9be9-459b-8f11-a06b7a718d20
55fa56f1-1b79-4292-982e-db644dd6dd5a	2024-05-05 05:52:48.085	2024-05-05 06:19:38.784502	2024-05-05 06:19:20.808	FOODS	1	9000	122f0364-9be9-459b-8f11-a06b7a718d20
0f13def6-ab56-4b41-8f99-393557ce844d	2024-05-05 06:03:25.591	2024-05-05 00:00:00	2024-05-05 06:03:25.591	FOODS		12000	122f0364-9be9-459b-8f11-a06b7a718d20
\.


--
-- TOC entry 3353 (class 0 OID 342686)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, created_date, deleted_date, updated_date, email_addres, password, username) FROM stdin;
346aacbe-7c4e-4774-ad85-20a45a921d28	2024-05-04 11:11:00.947721	2024-05-05 22:14:57.394552	2024-05-05 22:14:37.879756	khai	yyy	khai
cd12a5ae-5da2-46e9-8f40-c899ba4f6bba	2024-05-05 23:19:22.722647	\N	\N	yoyo	123	yoyo
788825c9-41c7-413a-8b21-4709edbf5e92	2024-05-05 23:52:53.35706	\N	\N	yono@mail.com	445	yono
\.


--
-- TOC entry 3194 (class 2606 OID 342666)
-- Name: merchant merchant_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.merchant
    ADD CONSTRAINT merchant_pkey PRIMARY KEY (id);


--
-- TOC entry 3196 (class 2606 OID 342672)
-- Name: order_detail order_detail_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT order_detail_pkey PRIMARY KEY (id);


--
-- TOC entry 3198 (class 2606 OID 342677)
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- TOC entry 3200 (class 2606 OID 342685)
-- Name: product product_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT product_pkey PRIMARY KEY (id);


--
-- TOC entry 3202 (class 2606 OID 342692)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3205 (class 2606 OID 342703)
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3203 (class 2606 OID 342698)
-- Name: order_detail fkb8bg2bkty0oksa3wiq5mp5qnc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkb8bg2bkty0oksa3wiq5mp5qnc FOREIGN KEY (product_id) REFERENCES public.product(id);


--
-- TOC entry 3206 (class 2606 OID 342708)
-- Name: product fkk47qmalv2pg906heielteubu7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.product
    ADD CONSTRAINT fkk47qmalv2pg906heielteubu7 FOREIGN KEY (merchant_id) REFERENCES public.merchant(id);


--
-- TOC entry 3204 (class 2606 OID 342693)
-- Name: order_detail fkrws2q0si6oyd6il8gqe2aennc; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_detail
    ADD CONSTRAINT fkrws2q0si6oyd6il8gqe2aennc FOREIGN KEY (order_id) REFERENCES public.orders(id);


-- Completed on 2024-05-06 00:17:15

--
-- PostgreSQL database dump complete
--

